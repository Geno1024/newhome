/*
 * @author geno1024
 * @date 2024-08-04 18:18:13
 */
 
#ifndef NEWHOME_PACKAGE1_H
#define NEWHOME_PACKAGE1_H

#include <stdint.h>
#include <stdio.h>

typedef enum NHPackage$1CompressionMethod
{
    Plain,
    Gzip,
    Xz
} NHPackage$1CompressionMethod;

typedef struct NHPackage$1Metadata
{
    int32_t keyLength;
    char *key;
    int32_t valueLength;
    char *value;
} NHPackage$1Metadata;

typedef struct NHPackage$1FileEntry
{
    int32_t fileNameLength;
    char *fileName;
    NHPackage$1CompressionMethod compressionMethod;
    int32_t compressedSize;
    int32_t uncompressedSize;
    char *compressedBody;
} NHPackage$1FileEntry;

typedef struct NHPackage$1
{
    int32_t magic;
    int16_t majorVersion;
    int16_t minorVersion;
    int64_t timestamp;
    int32_t packageNameLength;
    char *packageName;
    int32_t metadataCount;
    NHPackage$1Metadata **metadata;
    int32_t fileEntryCount;
    NHPackage$1FileEntry **fileEntries;
} NHPackage$1;

NHPackage$1 *NHPackage$1New();

void NHPackage$1Delete(NHPackage$1 *package);

NHPackage$1 *NHPackage$1SetTimestamp(NHPackage$1 *package, int64_t timestamp);

NHPackage$1 *NHPackage$1SetPackageName(NHPackage$1 *package, char *name);

NHPackage$1 *NHPackage$1PutMetadata(NHPackage$1 *package, char *key, char *value);

NHPackage$1Metadata *NHPackage$1GetMetadata(NHPackage$1 *package, char *key);

int NHPackage$1ExistsMetadata(NHPackage$1 *package, char *key);

NHPackage$1 *NHPackage$1DeleteMetadata(NHPackage$1 *package, char *key);

void NHPackage$1Save(NHPackage$1 *package, FILE *file);

#endif // NEWHOME_PACKAGE1_H
