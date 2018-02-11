package pv.dotai.datai.message.datast;

import pv.dotai.datai.util.BitStream;

public enum FieldPathOP {

	PlusOne(36271) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) + 1);
		}
	},
	PlusTwo(10334) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) + 2);
		}
	},
	PlusThree(1375) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) + 3);
		}
	},
	PlusFour(646) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) + 4);
		}
	},
	PlusN(4128) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) + bs.readFieldPathBitVar() + 5);
		}
	},
	PushOneLeftDeltaZeroRightZero(35) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.add(0);
		}
	},
	PushOneLeftDeltaZeroRightNonZero(3) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.add(bs.readFieldPathBitVar());
		}
	},
	PushOneLeftDeltaOneRightZero(521) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.path.size() - 1) + 1);
			fp.path.add(0);
		}
	},
	PushOneLeftDeltaOneRightNonZero(2942) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.path.size() - 1) + 1);
			fp.path.add(bs.readFieldPathBitVar());
		}
	},
	PushOneLeftDeltaNRightZero(560) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) +  bs.readFieldPathBitVar());
			fp.path.add(0);
		}
	},
	PushOneLeftDeltaNRightNonZero(471) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) +  bs.readFieldPathBitVar() + 2);
			fp.path.add(bs.readFieldPathBitVar() + 1);
		}
	},
	PushOneLeftDeltaNRightNonZeroPack6Bits(10530) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) +  bs.readBits(3) + 2);
			fp.path.add(bs.readBits(3) + 1);
		}
	},
	PushOneLeftDeltaNRightNonZeroPack8Bits(251) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) +  bs.readBits(4) + 2);
			fp.path.add(bs.readBits(4) + 1);
		}
	},
	PushTwoLeftDeltaZero(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.add(bs.readFieldPathBitVar());
			fp.path.add(bs.readFieldPathBitVar());
		}
	},
	PushTwoPack5LeftDeltaZero(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.add(bs.readBits(5));
			fp.path.add(bs.readBits(5));
		}
	},
	PushThreeLeftDeltaZero(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.add(bs.readFieldPathBitVar());
			fp.path.add(bs.readFieldPathBitVar());
			fp.path.add(bs.readFieldPathBitVar());
		}
	},
	PushThreePack5LeftDeltaZero(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.add(bs.readBits(5));
			fp.path.add(bs.readBits(5));
			fp.path.add(bs.readBits(5));
		}
	},
	PushTwoLeftDeltaOne(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) + 1);
			fp.path.add(bs.readFieldPathBitVar());
			fp.path.add(bs.readFieldPathBitVar());
		}
	},
	PushTwoPack5LeftDeltaOne(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) + 1);
			fp.path.add(bs.readBits(5));
			fp.path.add(bs.readBits(5));
		}
	},
	PushThreeLeftDeltaOne(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) + 1);
			fp.path.add(bs.readFieldPathBitVar());
			fp.path.add(bs.readFieldPathBitVar());
			fp.path.add(bs.readFieldPathBitVar());
		}
	},
	PushThreePack5LeftDeltaOne(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) + 1);
			fp.path.add(bs.readBits(5));
			fp.path.add(bs.readBits(5));
			fp.path.add(bs.readBits(5));
		}
	},
	PushTwoLeftDeltaN(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) + bs.readBitVar() + 2);
			fp.path.add(bs.readFieldPathBitVar());
			fp.path.add(bs.readFieldPathBitVar());
		}
	},
	PushTwoPack5LeftDeltaN(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) + bs.readBitVar() + 2);
			fp.path.add(bs.readBits(5));
			fp.path.add(bs.readBits(5));
		}
	},
	PushThreeLeftDeltaN(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) + bs.readBitVar() + 2);
			fp.path.add(bs.readFieldPathBitVar());
			fp.path.add(bs.readFieldPathBitVar());
			fp.path.add(bs.readFieldPathBitVar());
		}
	},
	PushThreePack5LeftDeltaN(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) + bs.readBitVar() + 2);
			fp.path.add(bs.readBits(5));
			fp.path.add(bs.readBits(5));
			fp.path.add(bs.readBits(5));
		}
	},
	PushN(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			int n = bs.readBitVar();
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.fp.path.size() - 1) +  bs.readBitVar());
			for (int i = 0; i < n; i++) {
				fp.path.add(bs.readFieldPathBitVar());
			}
		}
	},
	PushNAndNonTopographical(310) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			for (int i = 0; i <= fp.path.size() - 1; i++) {
				if (bs.nextBit() == 1) {
					fp.path.set(i, fp.path.get(fp.fp.path.size() - 1) +  bs.readVarSInt() + 1);
				}
			}
			int c = bs.readBitVar();
			for (int i = 0; i < c; i++) {
				fp.path.add(bs.readFieldPathBitVar());
			}
		}
	},
	PopOnePlusOne(2) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.remove(fp.path.size() - 1);
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.path.size() - 1) + 1);
		}
	},
	PopOnePlusN(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.remove(fp.path.size() - 1);
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.path.size() - 1) + bs.readFieldPathBitVar() + 1);
		}
	},
	PopAllButOnePlusOne(1837) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			int first = fp.path.get(0);
			fp.path.clear();
			fp.path.add(first + 1);
		}
	},
	PopAllButOnePlusN(149) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			int first = fp.path.get(0);
			fp.path.clear();
			fp.path.add(first + bs.readFieldPathBitVar() + 1);
		}
	},
	PopAllButOnePlusNPack3Bits(300) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			int first = fp.path.get(0);
			fp.path.clear();
			fp.path.add(first + bs.readBits(3) + 1);
		}
	},
	PopAllButOnePlusNPack6Bits(634) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			int first = fp.path.get(0);
			fp.path.clear();
			fp.path.add(first + bs.readBits(6) + 1);
		}
	},
	PopNPlusOne(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			int n = bs.readFieldPathBitVar();
			for (int i = 0; i < n; i++) {
				fp.path.remove(fp.path.size() - 1);
			}
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.path.size() - 1) + 1);
		}
	},
	PopNPlusN(0) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			int n = bs.readFieldPathBitVar();
			for (int i = 0; i < n; i++) {
				fp.path.remove(fp.path.size() - 1);
			}
			fp.path.set(fp.path.size() - 1, fp.path.get(fp.path.size() - 1) + bs.readVarSInt());
		}
	},
	PopNAndNonTopographical(1) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			int n = bs.readFieldPathBitVar();
			for (int i = 0; i < n; i++) {
				fp.path.remove(fp.path.size() - 1);
			}
			for (int i = 0; i <= fp.path.size() - 1; i++) {
				if (bs.nextBit() == 1) {
					fp.path.set(i, fp.path.get(i) +  bs.readVarSInt());
				}
			}
		}
	},
	NonTopoComplex(76) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			for (int i = 0; i <= fp.path.size() - 1; i++) {
				if (bs.nextBit() == 1) {
					fp.path.set(i, fp.path.get(i) +  bs.readVarSInt());
				}
			}
		}
	},
	NonTopoPenultimatePluseOne(271) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.path.set(fp.path.size() - 2, fp.path.get(fp.path.size() - 2) + 1);
		}
	},
	NonTopoComplexPack4Bits(99) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			for (int i = 0; i <= fp.path.size() - 1; i++) {
				if (bs.nextBit() == 1) {
					fp.path.set(i, fp.path.get(i) +  bs.readBits(4) - 7);
				}
			}
		}
	},
	FieldPathEncodeFinish(25474) {
		@Override
		public void execute(FieldPath fp, BitStream bs) {
			fp.setFinished(true);
		}
	};

	private final int weight;

	FieldPathOP(int weight) {
		this.weight = weight;
	}

	public void execute(FieldPath fp, BitStream bs) {
		throw new UnsupportedOperationException(String.format("FieldOp '%s' not implemented!", this.toString()));
	}

	public int getWeight() {
		return weight;
	}
}
