#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>

#define PATH_MAX 2000

extern int errno;

// java --enable-preview -jar projet-ia-echecs.jar 

int main(int argc, char** argv) {
    // chdir("./build");
    char cwd[PATH_MAX];
    char* args[] = {"java", "--enable-preview", "-jar", "./projet-ia-echecs.jar", NULL};
    execvp(args[0], args);
    perror("");
}
