#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static char staticBuffer[1000];

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



int main() {

    char** first = (char**)malloc(sizeof(char*));
    *first = (char*)malloc(sizeof(char) * strlen("Hello world"));
    strcpy(*first, "Hello world");
    char** second = (char**)malloc(sizeof(char*));
    *second = (char*)malloc(sizeof(char) * strlen("Ciao mondo"));
    strcpy(*second, "Ciao mondo");

    printf("%s\n", *stringConcatenation_(first, second));

    int b = 1;
    printf("%s\n", *fromIntegerToString_(b));

    double a = 134523250.9085943759;
    printf("%s\n", *fromDoubleToString_(a));

    return 0;
}
