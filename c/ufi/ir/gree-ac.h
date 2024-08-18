/*
 * @author geno1024
 * @date 2024-08-03 00:25:11
 */

#ifndef NEWHOME_GREE_AC_V1_H
#define NEWHOME_GREE_AC_V1_H

typedef enum UfiIrGreeacMode
{
    UfiIrGreeacMode_Auto = 0b000,
    UfiIrGreeacMode_Cold = 0b100,
    UfiIrGreeacMode_Dehumid = 0b010,
    UfiIrGreeacMode_Wind = 0b110,
    UfiIrGreeacMode_Hot = 0b001
} UfiIrGreeacMode;

typedef enum UfiIrGreeacOnoff
{
    UfiIrGreeacOnoff_Off,
    UfiIrGreeacOnoff_On
} UfiIrGreeacOnoff;

typedef enum UfiIrGreeacFanspeed
{
    UfiIrGreeacFanspeed_Auto = 0b00,
    UfiIrGreeacFanspeed_Weak = 0b10,
    UfiIrGreeacFanspeed_Medium = 0b01,
    UfiIrGreeacFanspeed_Strong = 0b11
} UfiIrGreeacFanspeed;

typedef enum UfiIrGreeacSwipe
{
    UfiIrGreeacSwipe_Off,
    UfiIrGreeacSwipe_On
} UfiIrGreeacSwipe;

typedef enum UfiIrGreeacSleep
{
    UfiIrGreeacSleep_Off,
    UfiIrGreeacSleep_On
} UfiIrGreeacSleep;

typedef enum UfiIrGreeacTempC
{
    UfiIrGreeacTempC_16 = 0b0000,
    UfiIrGreeacTempC_17 = 0b1000,
    UfiIrGreeacTempC_18 = 0b0100,
    UfiIrGreeacTempC_19 = 0b1100,
    UfiIrGreeacTempC_20 = 0b0010,
    UfiIrGreeacTempC_21 = 0b1010,
    UfiIrGreeacTempC_22 = 0b0110,
    UfiIrGreeacTempC_23 = 0b1110,
    UfiIrGreeacTempC_24 = 0b0001,
    UfiIrGreeacTempC_25 = 0b1001,
    UfiIrGreeacTempC_26 = 0b0101,
    UfiIrGreeacTempC_27 = 0b1101,
    UfiIrGreeacTempC_28 = 0b0011,
    UfiIrGreeacTempC_29 = 0b1011,
    UfiIrGreeacTempC_30 = 0b0111,
} UfiIrGreeacTempC;

typedef enum UfiIrGreeacTempF
{
    UfiIrGreeacTempF_61 = 0b0000,
    UfiIrGreeacTempF_62 = 0b1000,
    UfiIrGreeacTempF_63 = 0b1000,
    UfiIrGreeacTempF_64 = 0b0100,
    UfiIrGreeacTempF_65 = 0b0100,
    UfiIrGreeacTempF_66 = 0b1100,
    UfiIrGreeacTempF_67 = 0b1100,
    UfiIrGreeacTempF_68 = 0b0010,
    UfiIrGreeacTempF_69 = 0b1010,
    UfiIrGreeacTempF_70 = 0b1010,
    UfiIrGreeacTempF_71 = 0b0110,
    UfiIrGreeacTempF_72 = 0b0110,
    UfiIrGreeacTempF_73 = 0b1110,
    UfiIrGreeacTempF_74 = 0b1110,
    UfiIrGreeacTempF_75 = 0b0001,
    UfiIrGreeacTempF_76 = 0b0001,
    UfiIrGreeacTempF_77 = 0b1001,
    UfiIrGreeacTempF_78 = 0b0101,
    UfiIrGreeacTempF_79 = 0b0101,
    UfiIrGreeacTempF_80 = 0b1101,
    UfiIrGreeacTempF_81 = 0b1101,
    UfiIrGreeacTempF_82 = 0b0011,
    UfiIrGreeacTempF_83 = 0b0011,
    UfiIrGreeacTempF_84 = 0b1011,
    UfiIrGreeacTempF_85 = 0b1011,
    UfiIrGreeacTempF_86 = 0b0111,
} UfiIrGreeacTempF;

typedef union UfiIrGreeacTemp
{
    UfiIrGreeacTempC tempC : 4;
    UfiIrGreeacTempF tempF : 4;
} UfiIrGreeacTemp;

/**
 * 1        self.mode = buffer[1:4][::-1]
  1      self.on = buffer[4:5]
   1     self.fanspeed = buffer[5:7][::-1]
     1   self.swipe = buffer[7:8]
     1   self.sleep = buffer[8:9]
     1   self.temp = buffer[9:13][::-1]
        self.timer = buffer[13:21][::-1]
        self.strong = buffer[21:22]
        self.light = buffer[22:23]
        self.health = buffer[23:24]
        self.dry = buffer[24:25]
        self.swap = buffer[25:26]
        27: temp2
        self.reserved = "0001010010"
        self.udstate = buffer[37:41][::-1]
        self.lrstate = buffer[41:45][::-1]
        self.temploc = buffer[45:47][::-1]
        self.reserved2 = "0001000000000000"
        self.es = buffer[63:64][::-1] #  节能
        self.hot = buffer[64:65][::-1] #  辅热
        self.cksum = buffer[65:69][::-1] #  校验

        if self.version >= 2:
            self.windauto = buffer[77:78][::-1] # 扫风有动
            self.tempx0 = buffer[79:83][::-1] # 温度 -13 >> 4
            self.silent = buffer[114:115][::-1] # 静音
            self.fanspeedx = buffer[127:130][::-1] # 00 -> auto
            self.eshare = buffer[131:134][::-1] # E 享
            self.tempx1 = buffer[135:139][::-1] #  温度 -13
        else:
        self.expectcksum = bin(
            int(self.buffer[1:5][::-1], base=2) +
            int(self.buffer[9:13][::-1], base=2) +
            int(self.buffer[17:21][::-1], base=2) +
            int(self.buffer[25:29][::-1], base=2) +
            int(self.buffer[41:45][::-1], base=2) +
            int(self.buffer[49:53][::-1], base=2) +
            int(self.buffer[57:61][::-1], base=2) +
            0b1010
            # (1 if int(self.swap) == 1 else -1) +
            # (1 if int(self.lrstate) >= 1 else -1) +
        )[::-1][0:4]
 */

typedef struct UfiIrGreeacSeq00 {
    UfiIrGreeacMode mode : 3;
    UfiIrGreeacOnoff onoff : 1;
    UfiIrGreeacFanspeed fanspeed : 2;
    UfiIrGreeacSwipe swipe : 1;
    UfiIrGreeacSleep sleep : 1;
    UfiIrGreeacTemp temp;
} UfiIrGreeacSeq00;

#endif //NEWHOME_GREE_AC_V1_H
