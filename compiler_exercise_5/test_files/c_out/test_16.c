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
/*STANDARD IMPORT*/




/* FUNCTION DECLARATIONS */
int fun1_fun(  );
/* FUNCTION DECLARATIONS */
/* GLOBAL VARIABLES DECLARATION */
int a;

int b;

char** c;

char** d;

char** e;

double f;

int g;

/* GLOBAL VARIABLES DECLARATION */
/* FUNCTION DEFINITIONS */
int fun1_fun(  )
{
int  x1 = 3, x2 = 5;
x1 =  ( (b + x1 ) + a );
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "1. Risultato atteso 13" ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ),x1);
return 5;
}
/* FUNCTION DEFINITIONS */
void initializeGlobalVariable_()
{
/* GLOBAL VARIABLES DEFAULT INITIALIZATIONS */
a = 0;
b = 0;
c = fromConstantStringToHeap_("");
d = fromConstantStringToHeap_("");
e = fromConstantStringToHeap_("");
f = 0.0;
g = 0;
/* GLOBAL VARIABLES DEFAULT INITIALIZATIONS */
/* GLOBAL VARIABLES DEFINITION */
a = 0;
b = 10;
c = fromConstantStringToHeap_("");
d =  (stringConcatenation_(c,fromConstantStringToHeap_( "1" )) );
e =  (stringConcatenation_(e,fromConstantStringToHeap_( "12" )) );
f =  ( (f + b ) + g );
g = 0;
/* GLOBAL VARIABLES DEFINITION */
}
int main()
{
initializeGlobalVariable_();
char**  a = fromConstantStringToHeap_(""),** b = fromConstantStringToHeap_("");
double  n1 = 0.0, n2 = 1.0;
int/*ex boolean*/  bool1 = 0, bool2 = 0;
fun1_fun();
printf( "%s%s%s\n" ,  *fromConstantStringToHeap_( "2. Risultato atteso 1 " ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ), *d);
printf( "%s%s%s\n" ,  *fromConstantStringToHeap_( "3. Risultato atteso " ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ), *c);
printf( "%s%s%s\n" ,  *fromConstantStringToHeap_( "4. Risultato atteso 1 " ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ), *d);
printf( "%s%s%lf\n" ,  *fromConstantStringToHeap_( "5. Risultato atteso 1.0 " ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ), ( (n1 + n2 ) +  (n1 * n2 ) ));
*b = *cloneString_( (stringConcatenation_( (stringConcatenation_(b,a) ),fromIntegerToString_(11)) ));
printf( "%s%s%s\n" ,  *fromConstantStringToHeap_( "6. Risultato atteso 11 " ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ), *b);
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "7. Risultato atteso 0 " ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ),bool1);
bool2 = 1;
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "8. Risultato atteso 1 " ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ), (bool1 || bool2 ));
printf( "%s%s%s\n" ,  *fromConstantStringToHeap_( "9. Risultato atteso 12 " ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ), *e);
printf( "%s%s%lf\n" ,  *fromConstantStringToHeap_( "10. Risultato atteso 10.0 " ), *fromConstantStringToHeap_( " --Risultato ottenuto: " ),f);
return 0;
}
