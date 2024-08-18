/*
 * @author geno1024
 * @date 2024-07-05 03:28:29
 */

#ifndef NEWHOME_UFI_IFS_HTTP_H
#define NEWHOME_UFI_IFS_HTTP_H

enum HTTP_METHOD
{
    GET,
    POST
};

enum HTTP_VERSION
{
    `
};

typedef struct HTTP_HEADER
{
    char *key;
    char *value;
} HTTP_HEADER;

typedef struct HTTP_REQUEST_HEADER
{
    enum HTTP_METHOD method;

} HTTP_REQUEST_HEADER;

#endif //NEWHOME_UFI_IFS_HTTP_H
