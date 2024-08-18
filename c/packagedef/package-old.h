/*
 * @author geno1024
 * @date 2024-07-28 10:08:20
 */

#ifndef NEWHOME_PACKAGE_H
#define NEWHOME_PACKAGE_H

#include <stdint.h>
#include <stdio.h>

typedef enum NHPackage$1CompressionMethod
{
    Plain,
    Deflate,
    Gzip
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
    char magic[4];
    char majorVersion[2];
    char minorVersion[2];
    int64_t timestamp;
    int32_t packageNameLength;
    char *packageName;
    int32_t metadataCount;
    NHPackage$1Metadata **metadata;
    int32_t fileEntryCount;
    NHPackage$1FileEntry **fileEntries;
} NHPackage$1;
#define NHPackage NHPackage$1

NHPackage$1 *NHPackage$1New();
#define NHPackageNew NHPackage$1New

void NHPackage$1Delete(NHPackage$1 *package);
#define NHPackageDelete NHPackage$1Delete

NHPackage$1 *NHPackage$1SetTimestamp(NHPackage$1 *package, int64_t timestamp);
#define NHPackageSetTimestamp NHPackage$1SetTimestamp

NHPackage$1 *NHPackage$1SetPackageName(NHPackage$1 *package, char *name);
#define NHPackageSetPackageName NHPackage$1SetPackageName

void NHPackage$1Save(NHPackage$1 *package, FILE *file);
#define NHPackageSave NHPackage$1Save

#endif //NEWHOME_PACKAGE_H
