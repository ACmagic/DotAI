/**
 * Slightly modified from https://github.com/skadistats/clarity/blob/f8e1fec8c5478b9ddca4aa6866c14fb18526b638/src/main/java/skadistats/clarity/decoder/unpacker/FloatQuantizedUnpacker.java
 */
package pv.dotai.datai.message.datast.decoder;

import pv.dotai.datai.util.BitStream;

/**
 * Decoder for network quantized floats
 * @since  1.0
 */
public class FloatQuantizedDecoder {

	private static final int QFE_ROUNDDOWN = 0x1;
    private static final int QFE_ROUNDUP = 0x2;
    private static final int QFE_ENCODE_ZERO_EXACTLY = 0x4;
    private static final int QFE_ENCODE_INTEGERS_EXACTLY = 0x8;
	public static final long[] MASKS = { 0x0L, 0x1L, 0x3L, 0x7L, 0xfL, 0x1fL, 0x3fL, 0x7fL, 0xffL, 0x1ffL, 0x3ffL, 0x7ffL, 0xfffL, 0x1fffL, 0x3fffL, 0x7fffL, 0xffffL, 0x1ffffL, 0x3ffffL, 0x7ffffL, 0xfffffL, 0x1fffffL, 0x3fffffL, 0x7fffffL, 0xffffffL, 0x1ffffffL, 0x3ffffffL, 0x7ffffffL, 0xfffffffL, 0x1fffffffL, 0x3fffffffL, 0x7fffffffL, 0xffffffffL, 0x1ffffffffL, 0x3ffffffffL, 0x7ffffffffL, 0xfffffffffL, 0x1fffffffffL, 0x3fffffffffL, 0x7fffffffffL, 0xffffffffffL, 0x1ffffffffffL, 0x3ffffffffffL, 0x7ffffffffffL, 0xfffffffffffL, 0x1fffffffffffL, 0x3fffffffffffL, 0x7fffffffffffL, 0xffffffffffffL, 0x1ffffffffffffL, 0x3ffffffffffffL, 0x7ffffffffffffL, 0xfffffffffffffL, 0x1fffffffffffffL, 0x3fffffffffffffL, 0x7fffffffffffffL, 0xffffffffffffffL, 0x1ffffffffffffffL, 0x3ffffffffffffffL, 0x7ffffffffffffffL, 0xfffffffffffffffL, 0x1fffffffffffffffL, 0x3fffffffffffffffL, 0x7fffffffffffffffL, 0xffffffffffffffffL};

    private int bitCount;
    private float minValue;
    private float maxValue;
    private int flags;
    private int encodeFlags;

    private float highLowMultiplier;
    private float decodeMultiplier;

    public FloatQuantizedDecoder(String fieldName, int bitCount, int flags, float minValue, float maxValue) {
        this.bitCount = bitCount;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.flags = flags;
        this.encodeFlags = computeEncodeFlags(flags);
        initialize();
    }

    private int computeEncodeFlags(int f) {
        // If the min or max value is exactly zero and we are encoding min or max exactly, then don't need zero flag
        if ((minValue == 0.0f && (f & QFE_ROUNDDOWN) != 0) || (maxValue == 0.0f && (f & QFE_ROUNDUP) != 0)) {
            f &= ~QFE_ENCODE_ZERO_EXACTLY;
        }

        // If specified encode zero but min or max actual value is zero, then convert that encode directive to be encode min or max exactly instead
        if (minValue == 0.0f && (f & QFE_ENCODE_ZERO_EXACTLY) != 0) {
            f |= QFE_ROUNDDOWN;
            f &= ~QFE_ENCODE_ZERO_EXACTLY;
        }
        if (maxValue == 0.0f && (f & QFE_ENCODE_ZERO_EXACTLY) != 0) {
            f |= QFE_ROUNDUP;
            f &= ~QFE_ENCODE_ZERO_EXACTLY;
        }

        // If the range doesn't span across zero, then also don't need the zero flag
        if (!(minValue < 0.0f && maxValue > 0.0f)) {
            f &= ~QFE_ENCODE_ZERO_EXACTLY;
        }

        if ((f & QFE_ENCODE_INTEGERS_EXACTLY) != 0) {
            // Wipes out all other flags
            f &= ~(QFE_ROUNDUP | QFE_ROUNDDOWN | QFE_ENCODE_ZERO_EXACTLY);
        }

        return f;
    }

    private void initialize() {
        float offset;
        int quanta = (1 << bitCount);

        if ((flags & QFE_ROUNDDOWN) != 0) {
            offset = ((maxValue - minValue) / quanta);
            maxValue -= offset;
        } else if ((flags & QFE_ROUNDUP) != 0) {
            offset = ((maxValue - minValue) / quanta);
            minValue += offset;
        }

        if ((flags & QFE_ENCODE_INTEGERS_EXACTLY) != 0) {
            int delta = ((int) minValue) - ((int) maxValue);
            int trueRange = (1 << (int) Math.log((Math.max(delta, 1)) / Math.log(2)) + 1);

            int nBits = this.bitCount;
            while ((1 << nBits) < trueRange) {
                ++nBits;
            }
            if (nBits > bitCount) {
                bitCount = nBits;
                quanta = (1 << bitCount);
            }

            float floatRange = (float) trueRange;
            offset = (floatRange / (float) quanta);
            maxValue = minValue + floatRange - offset;
        }

        highLowMultiplier = assignRangeMultiplier(bitCount, maxValue - minValue);
        decodeMultiplier = 1.0f / (quanta - 1);

        if ((encodeFlags & QFE_ROUNDDOWN) != 0) {
            if (quantize(minValue) == minValue) {
                encodeFlags &= ~QFE_ROUNDDOWN;
            }
        }
        if ((encodeFlags & QFE_ROUNDUP) != 0) {
            if (quantize(maxValue) == maxValue) {
                encodeFlags &= ~QFE_ROUNDUP;
            }
        }
        if ((encodeFlags & QFE_ENCODE_ZERO_EXACTLY) != 0) {
            if (quantize(0.0f) == 0.0f) {
                encodeFlags &= ~QFE_ENCODE_ZERO_EXACTLY;
            }
        }
    }

    private float assignRangeMultiplier(int nBits, float range) {
        long highValue;

        if (nBits == 32) {
            highValue = 0xFFFFFFFEL;
        } else {
            highValue = MASKS[nBits];
        }

        float highLowMul;
        if (Math.abs(range) <= 0.001) {
            highLowMul = (float) highValue;
        } else {
            highLowMul = highValue / range;
        }

        // If the precision is messing us up, then adjust it so it won't.
        if ((long) (highLowMul * range) > highValue || (highLowMul * range) > (double) highValue) {
            // Squeeze it down smaller and smaller until it's going to produce an integer
            // in the valid range when given the highest value.
            float multipliers[] = {0.9999f, 0.99f, 0.9f, 0.8f, 0.7f};
            int i;
            for (i = 0; i < multipliers.length; i++) {
                highLowMul = (highValue / range) * multipliers[i];
                if ((long) (highLowMul * range) > highValue || (highLowMul * range) > (double) highValue)
                    continue;
                break;
            }
        }

        return highLowMul;
    }

    private float quantize(float value) {
        if (value < minValue) {
            return minValue;
        } else if (value > maxValue) {
            return maxValue;
        }
        int i = (int) ((value - minValue) * highLowMultiplier);
        return minValue + (maxValue - minValue) * ((float) i * decodeMultiplier);
    }

    public Float decode(BitStream bs) {
        if ((encodeFlags & QFE_ROUNDDOWN) != 0 && bs.nextBit() == 1) {
            return minValue;
        }
        if ((encodeFlags & QFE_ROUNDUP) != 0 && bs.nextBit() == 1) {
            return maxValue;
        }
        if ((encodeFlags & QFE_ENCODE_ZERO_EXACTLY) != 0 && bs.nextBit() == 1) {
            return 0.0f;
        }
        float v = bs.readBits(bitCount) * decodeMultiplier;
        return minValue + (maxValue - minValue) * v;
    }

}
