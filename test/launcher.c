#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>

#define PATH_MAX 2000

extern int errno;

// java --enable-preview -jar projet-ia-echecs.jar 

int main() {
    // chdir("./build");
    char cwd[PATH_MAX];
    getcwd(cwd, sizeof(cwd));
    strcat(cwd, "/projet-ia-echecs.jar");
    char* args[] = {"java", "--enable-preview", "-jar", cwd, NULL};
    puts(cwd);
    execvp(args[0], args);
    perror("");
}
