# DotA 2 (Source 2) Demo Files

This document will try to explain as well as possible the format and the data in the DotA 2 *.dem* files.

## Basics

### The Header

Demo file header starts with *"PBDEMS2*\\*0"* for Valve's Source 2 Engine files and *"PBUFDEM*\\*0"* for Valve's Source 1 Engine files. This project only focuses on **Source 2** files so from now on all the infomations given only apply to these files.

The header then contains two 4-bytes integers, still haven't found what they are used for but they probably specify a byte offset to some information in the file.

### The Body

DotA 2 uses google's protobuf to serialize it's messages, the file (except the header) is a serie of protobuf-encoded packets, the protobuf definition for DotA 2 can be found [here](https://github.com/SteamRE/SteamKit/tree/master/Resources/Protobufs/dota). 

The body of the file is made of entries of this type: 

| Meaning    | Type        |
| ---------- | ----------- |
| Command ID | Varint      |
| Tick       | Varint      |
| Size       | Varint      |
| Buffer     | Byte Buffer |

VarInts are integers that are encoded on a variable number of bytes to save space, the way this is encoded is descibed [here](https://developers.google.com/protocol-buffers/docs/encoding#varints).

**Command ID** is the identifier of the buffer to parse. It refers to the EDemoCommands enum's numbers.
But there's a twist, if all the bits from EDemoCommands.DEM_IsCompressed's number are set to 1, it means that the buffer is compressed with snappy library and has to be uncompressed before parsing.
Then of course you have to switch those bits to 0 to get the actual command ID.

**Tick** is the number of tick in the game when the command was emitted, this of course does not refer to any real world time and is just a relative timing from the game start.

**Size** is the size of the protobuf message (in bytes)

**Buffer** is the protobuf-encoded message the you have to parse (after uncompressing if needed).

The commands in the current protocol are:

```protobuf
enum EDemoCommands {
  DEM_Error = -1;
  DEM_Stop = 0;
  DEM_FileHeader = 1;
  DEM_FileInfo = 2;
  DEM_SyncTick = 3;
  DEM_SendTables = 4;
  DEM_ClassInfo = 5;
  DEM_StringTables = 6;
  DEM_Packet = 7;
  DEM_SignonPacket = 8;
  DEM_ConsoleCmd = 9;
  DEM_CustomData = 10;
  DEM_CustomDataCallbacks = 11;
  DEM_UserCmd = 12;
  DEM_FullPacket = 13;
  DEM_SaveGame = 14;
  DEM_SpawnGroups = 15;
  DEM_Max = 16;
  DEM_IsCompressed = 64;
}
```
## Embedded Data

Now that you have a serie of protobuf messages, you need to decode what's inside **DEM_Packet, DEM_SignonPacket** and **DEM_FullPacket**.

DEM_Packet and DEM_SignonPacket are basically the same, signon packets are just sent prior to the game start.

They both contain embedded data which is encoded in the following way:

| Meaning    | Type                         |
| ---------- | ---------------------------- |
| Command ID | Bitvar                       |
| Size       | Varint                       |
| Buffer     | Non byte-aligned byte buffer |

The trick to be able to decode this data is to read it bit by bit because data is no longer byte-aligned so we need to create a bit stream.

The **command ID** is written in the following way: 

```
Byte No: |       0       ||       1       ||       2       ||       3       ||      4  //
Bit No:  |0 1 2 3|4 5 6 7||0 1 2 3|4 5 6 7||0 1 2 3|4 5 6 7||0 1 2 3|4 5 6 7||0 1      // 
         |-------|-------||-------|-------||-------|-------||-------|-------||---     //
Value:   |X Y 0 0|0 0 1 1||1 1 2 2|2 2 3 3||3 3 3 3|3 3 3 3||3 3 3 3|3 3 3 3||3 3     //
```

All the bit tagged as X, Y and 0 must be read.

The "0" bits are data (the 4 **least significant** bits) whereas the X and Y are infos but are not part of the data.
If Y is set, we have to read 4 extra bits (the ones tagged as *1*).
If X is set, we have to read 8 extra bits (the ones tagged as *1* and *2*).
If X **and** Y are set, we have to read 28 extra bits (the ones tagged as *1*,  *2* and *3*).

The **size** defines the size of the buffer in **bytes**

The **buffer** is yet again a protobuf-encoded message containing either a SVC_Message or A NET_Message. (Maybe something else also). 



DEM_FullPacket contains a DEM_Packet which itself contains embedded data as described above.



DEM_SendTables are also encoded with embedded data but I haven't tried to read it yet.
