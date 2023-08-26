package limitedjext;

interface IColors {
    // Regular ANSI Color codes
    String RESET = "\033[0m";
    String BRED = "\033[31;1m";
    String BGREEN = "\033[32;1m";
    String BYELLOW = "\033[33;1m";
    String BBLYE = "\033[34;1m";
    String BMAGNETTA = "\033[35;1m";
    String BCYAN = "\033[36;1m";

    // Strings
    String S_OK = "[ %sOK%s ]  ".formatted(BGREEN, RESET);
    String S_WARN = "[%sWARN%s]  ".formatted(BYELLOW, RESET);
    String S_ERROR = "[%sERR%s ]  ".formatted(BRED, RESET);
}
