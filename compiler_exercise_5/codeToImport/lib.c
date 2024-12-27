#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static char staticBuffer[1000];

char** fromConstantStringToHeap_(char* string) {

    int size = strlen(string);

    char** newString = (char**)malloc(sizeof(char*));
    *newString = (char*)malloc((size+1)*sizeof(char));

    strcpy(*newString, string);

    return newString;
}

void inputString_(char** string) {

    scanf("%s", staticBuffer);
    int size = strlen(staticBuffer);

    *string = (char*)malloc((size+1) + sizeof(char));
    strcpy(*string, staticBuffer);
}

char** stringConcatenation_(char** first, char** second) {

    int sizeFirst = strlen(*first);
    int sizeSecond = strlen(*second);

    char** newString = (char**)malloc(sizeof(char*));
    *newString = (char*)malloc((sizeFirst + sizeSecond + 1) * sizeof(char));

    strcpy(*newString, *first);
    strcpy(*newString + sizeFirst, *second);

    return newString;
}

char** cloneString_(char** string) {

    int size = strlen(*string);

    char** newString = (char**)malloc(sizeof(char*));
    *newString = (char*)malloc((size+1)*sizeof(char));

    strcpy(*newString, *string);

    return newString;
}

char** fromIntegerToString_(int var) {
    sprintf(staticBuffer, "%d", var);
    int size = strlen(staticBuffer);

    char** newString = (char**)malloc(sizeof(char*));
    *newString = (char*)malloc((size + 1) * sizeof(char));

    strcpy(*newString, staticBuffer);

    return newString;
}

char** fromDoubleToString_(double var) {
    sprintf(staticBuffer, "%.20f", var);
    int size = strlen(staticBuffer);

    char** newString = (char**)malloc(sizeof(char*));
    *newString = (char*)malloc((size + 1) * sizeof(char));

    strcpy(*newString, staticBuffer);

    return newString;
}