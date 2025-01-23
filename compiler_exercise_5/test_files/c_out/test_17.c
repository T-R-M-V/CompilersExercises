/*STANDARD IMPORT*/
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

int equalForString_(char** s1, char** s2) {
    if(strcmp(*s1, *s2) == 0)
        return 1;

    return 0;
}

int notEqualForString_(char** s1, char** s2) {
    return ! equalForString_(s1, s2);
}
/*STANDARD IMPORT*/




/* FUNCTION DECLARATIONS */
double fun_fun( double d1,double *d2 );
/* FUNCTION DECLARATIONS */
/* GLOBAL VARIABLES DECLARATION */
double d;

/* GLOBAL VARIABLES DECLARATION */
/* FUNCTION DEFINITIONS */
double fun_fun( double d1,double *d2 )
{
int  n = 2;
double  d = 2.2;
if (  (*d2 == 0 ) )
{
*d2 = 1;
fun_fun(d1, &*d2);
}
d1 = 3;
*d2 =  ( (n + d ) + d1 );
return n;
}
/* FUNCTION DEFINITIONS */
void initializeGlobalVariable_()
{
/* GLOBAL VARIABLES DEFAULT INITIALIZATIONS */
d = 0.0;
/* GLOBAL VARIABLES DEFAULT INITIALIZATIONS */
/* GLOBAL VARIABLES DEFINITION */
d = 3;
/* GLOBAL VARIABLES DEFINITION */
}
int main()
{
initializeGlobalVariable_();
int  n1 = 4, n2 = 0;
double  d1 = 3.4, d2 = 2;
printf( "%s%s%lf%s%lf\n" ,  *fromConstantStringToHeap_( "1. Risultato atteso 3.4000   2.0000" ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ),d1, *fromConstantStringToHeap_( "   " ),d2);
d1 = 2;
d2 = 4.5;
printf( "%s%s%lf%s%lf\n" ,  *fromConstantStringToHeap_( "2. Risultato atteso 2.0000   4.5000" ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ),d1, *fromConstantStringToHeap_( "   " ),d2);
d = fun_fun(d1, &d2);
printf( "%s%s%d%s%lf\n" ,  *fromConstantStringToHeap_( "3. Risultato atteso  4   7.2000" ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ),n1, *fromConstantStringToHeap_( "   " ),d2);
d = fun_fun(3, &d2);
printf( "%s%s%lf%s%lf\n" ,  *fromConstantStringToHeap_( "4. Risultato atteso  2   4.2000" ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ),d, *fromConstantStringToHeap_( "   " ),d2);
d2 = 0;
d = fun_fun(n1, &d2);
printf( "%s%s%lf%s%lf\n" ,  *fromConstantStringToHeap_( "5. Risultato atteso  2   " ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ),d, *fromConstantStringToHeap_( "   " ),d2);
return 0;
}
