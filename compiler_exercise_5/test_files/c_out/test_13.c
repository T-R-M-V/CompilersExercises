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
char** fun2_fun(  );
void fun_fun( int n1,int n2,int n3,int/*ex boolean*/ b1,int/*ex boolean*/ b2,int/*ex boolean*/ b3,double d1,double d2 );
/* FUNCTION DECLARATIONS */
/* GLOBAL VARIABLES DECLARATION */
char** s1;

char** s2;

/* GLOBAL VARIABLES DECLARATION */
/* FUNCTION DEFINITIONS */
char** fun2_fun(  )
{
return cloneString_(fromConstantStringToHeap_( "fun2()" ));
}
void fun_fun( int n1,int n2,int n3,int/*ex boolean*/ b1,int/*ex boolean*/ b2,int/*ex boolean*/ b3,double d1,double d2 )
{
char**  s3 = fromConstantStringToHeap_( "ciao3" );
n1 = 4;
n2 = 5;
d1 = 1.15;
d2 = 4.4;
b1 =  ( ( (n1 < n2 ) &&  (1 >= 5 ) ) ||  ( (d1 != d2 ) &&  (n1 ==  (n2 - 1 ) ) ) );
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "1. Risultato atteso 1" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),b1);
n1 =  ( ( (n1 + n2 ) *  (3 - n1 ) ) / 4 );
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "2. Risultato atteso -2" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),n1);
d1 = 1.5;
d2 = 3.0;
n2 = 4;
d1 =  ( (2 +  (4 *  (n2 - d1 ) ) ) +  (n2 /(double) (d2) ) );
printf( "%s%s%lf\n" ,  *fromConstantStringToHeap_( "3. Risultato atteso 13.333" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),d1);
d1 = 1.5;
d2 = 3.3;
n2 = 4;
d1 =  ( ( ( (d1 - n2 ) * 4 ) +  (d2 /(double) (n2) ) ) + 2 );
printf( "%s%s%lf\n" ,  *fromConstantStringToHeap_( "4. Risultato atteso -7.175" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),d1);
b2 = 0;
b3 = 1;
d1 = 3.15;
n1 = 3;
b1 =  (b1 ||  (b2 &&  (!  (b1 &&  (d1 <= n1 ) ))  ) );
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "5. Risultato atteso 1" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),b1);
b2 = 0;
b1 =  ( ( (!  (n1 == n1 ))  && b1 ) || b2 );
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "6. Risultato atteso 0" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),b1);
n1 = 3;
d1 = 5.55;
*s3 = *cloneString_( (stringConcatenation_( (stringConcatenation_( (stringConcatenation_( (stringConcatenation_( (stringConcatenation_(fromIntegerToString_(n1),s1) ),fromDoubleToString_(d1)) ),fromIntegerToString_(n1)) ),fromConstantStringToHeap_( " || " )) ),fromDoubleToString_(d1)) ));
printf( "%s%s%s\n" ,  *fromConstantStringToHeap_( "7. Risultato atteso 3ciao15.553 || 5.55" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ), *s3);
*s3 = *cloneString_(fromConstantStringToHeap_( "ciao3" ));
*s3 = *cloneString_( (stringConcatenation_( (stringConcatenation_( (stringConcatenation_( (stringConcatenation_(fun2_fun(),fromConstantStringToHeap_( "---" )) ),s1) ),fromConstantStringToHeap_( ", " )) ),s2) ));
printf( "%s%s%s\n" ,  *fromConstantStringToHeap_( "8. Risultato atteso fun2()---ciao1, ciao2" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ), *s3);
n1 = 1;
n2 = 4;
b1 =  ( (!  ( (n1 < n2 ) &&  (n2 >= n1 ) ))  ||  (!  (!  (n1 != n1 )) )  );
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "9. Risultato atteso 0" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),b1);
d1 = 1.5;
d2 = 3.0;
b1 =  ( ( (d1 >= 10 ) &&  (!  (d2 == d2 ))  ) ||  (d1 == d2 ) );
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "10. Risultato atteso 0" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),b1);
}
/* FUNCTION DEFINITIONS */
void initializeGlobalVariable_()
{
/* GLOBAL VARIABLES DEFAULT INITIALIZATIONS */
s1 = fromConstantStringToHeap_("");
s2 = fromConstantStringToHeap_("");
/* GLOBAL VARIABLES DEFAULT INITIALIZATIONS */
/* GLOBAL VARIABLES DEFINITION */
s1 = fromConstantStringToHeap_( "ciao1" );
s2 = fromConstantStringToHeap_( "ciao2" );
/* GLOBAL VARIABLES DEFINITION */
}
int main()
{
initializeGlobalVariable_();
int  n1 = 0, n2 = 0, n3 = 0;
int/*ex boolean*/  b1 = 0, b2 = 0, b3 = 0;
double  d1 = 0.0, d2 = 0.0;
char**  s3 = fromConstantStringToHeap_( "ciao3" );
printf( "%s\n" ,  *fromConstantStringToHeap_( "Prima della funzione --------------------- \n" ));
fun_fun(n1,n2,n3,b1,b2,b3,d1,d2);
printf( "%s\n" ,  *fromConstantStringToHeap_( "Dopo la funzione----------------------\n" ));
n1 = 4;
n2 = 5;
d1 = 1.15;
d2 = 4.4;
b1 =  ( ( (n1 < n2 ) &&  (1 >= 5 ) ) ||  ( (d1 != d2 ) &&  (n1 ==  (n2 - 1 ) ) ) );
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "1. Risultato atteso 1" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),b1);
n1 =  ( ( (n1 + n2 ) *  (3 - n1 ) ) / 4 );
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "2. Risultato atteso -2" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),n1);
d1 = 1.5;
d2 = 3.0;
n2 = 4;
d1 =  ( (2 +  (4 *  (n2 - d1 ) ) ) +  (n2 /(double) (d2) ) );
printf( "%s%s%lf\n" ,  *fromConstantStringToHeap_( "3. Risultato atteso 13.333" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),d1);
d1 = 1.5;
d2 = 3.3;
n2 = 4;
d1 =  ( ( ( (d1 - n2 ) * 4 ) +  (d2 /(double) (n2) ) ) + 2 );
printf( "%s%s%lf\n" ,  *fromConstantStringToHeap_( "4. Risultato atteso -7.175" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),d1);
b2 = 0;
b3 = 1;
d1 = 3.15;
n1 = 3;
b1 =  (b1 ||  (b2 &&  (!  (b1 &&  (d1 <= n1 ) ))  ) );
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "5. Risultato atteso 1" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),b1);
b2 = 0;
b1 =  ( ( (!  (n1 == n1 ))  && b1 ) || b2 );
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "6. Risultato atteso 0" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),b1);
n1 = 3;
d1 = 5.55;
*s3 = *cloneString_( (stringConcatenation_( (stringConcatenation_( (stringConcatenation_( (stringConcatenation_( (stringConcatenation_(fromIntegerToString_(n1),s1) ),fromDoubleToString_(d1)) ),fromIntegerToString_(n1)) ),fromConstantStringToHeap_( " || " )) ),fromDoubleToString_(d1)) ));
printf( "%s%s%s\n" ,  *fromConstantStringToHeap_( "7. Risultato atteso 3ciao15.553 || 5.55" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ), *s3);
*s3 = *cloneString_(fromConstantStringToHeap_( "ciao3" ));
*s3 = *cloneString_( (stringConcatenation_( (stringConcatenation_( (stringConcatenation_( (stringConcatenation_(fun2_fun(),fromConstantStringToHeap_( "---" )) ),s1) ),fromConstantStringToHeap_( ", " )) ),s2) ));
printf( "%s%s%s\n" ,  *fromConstantStringToHeap_( "8. Risultato atteso fun2()---ciao1, ciao2" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ), *s3);
n1 = 1;
n2 = 4;
b1 =  ( (!  ( (n1 < n2 ) &&  (n2 >= n1 ) ))  ||  (!  (!  (n1 != n1 )) )  );
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "9. Risultato atteso 0" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),b1);
d1 = 1.5;
d2 = 3.0;
b1 =  ( ( (d1 >= 10 ) &&  (!  (d2 == d2 ))  ) ||  (d1 == d2 ) );
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "10. Risultato atteso 0" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ),b1);
*s1 = *cloneString_(fromConstantStringToHeap_( "ciao" ));
*s2 = *cloneString_(fromConstantStringToHeap_( "ciao" ));
*s3 = *cloneString_(fromConstantStringToHeap_( "no" ));
b1 = 1;
b2 = 0;
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "11. Risultato atteso 1" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ), ( (equalForString_(s1,s2) ) && b1 ));
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "12. Risultato atteso 0" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ), ( (equalForString_(s1,s3) ) || b2 ));
n1 = 3;
n2 = 4;
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "13. Risultato atteso 0" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ), ( (notEqualForString_(s1,s2) ) &&  (3 <= 4 ) ));
printf( "%s%s%d\n" ,  *fromConstantStringToHeap_( "14. Risultato atteso 1" ), *fromConstantStringToHeap_( "--Risultato ottenuto: " ), ( (notEqualForString_(s1,s3) ) &&  (3 <= 4 ) ));
return 0;
}
