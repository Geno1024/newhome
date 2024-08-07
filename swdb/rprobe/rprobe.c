/*
 * @author geno1024
 * @date 2024-08-06 23:53:46
 */

#include <stdio.h>
#include <dirent.h>
#include "rprobe.h"

int32_t charging()
{
#if defined(_WIN64)
#elif defined(__linux__)
    DIR *dir = opendir("/sys/class/power_supply/");
    struct dirent *entry;
    while ((entry = readdir(dir)) != NULL)
    {
        if (entry->d_name[0] == 'A')
        {
            char charge_file_dir[256];
            sprintf(charge_file_dir, "/sys/class/power_supply/%s/online", entry->d_name);
            FILE *charge_file_fp = fopen(charge_file_dir, "r");
            int32_t result;
            fscanf(charge_file_fp, "%d", &result);
            return result;
        }
    }
#endif
}

int32_t battery()
{
#if defined(_WIN64)
#elif defined(__linux__)
    DIR *dir = opendir("/sys/class/power_supply/");
    struct dirent *entry;
    while ((entry = readdir(dir)) != NULL)
    {
        if (entry->d_name[0] == 'B')
        {
            char battery_file_dir[256];
            sprintf(battery_file_dir, "/sys/class/power_supply/%s/capacity", entry->d_name);
            FILE *battery_file_fp = fopen(battery_file_dir, "r");
            int32_t result;
            fscanf(battery_file_fp, "%d", &result);
            return result;
        }
    }
#endif
    return -1;
}

int main(int argc, char *argv[])
{
    printf("Charge: %d\n", charging());
    printf("Battery: %d\n", battery());
}
