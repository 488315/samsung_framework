.class public final Lgov/nist/javax/sip/parser/ims/PathParser;
.super Lgov/nist/javax/sip/parser/AddressParametersParser;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# direct methods
.method public constructor <init>(Lgov/nist/javax/sip/parser/Lexer;)V
    .locals 0

    .line 2
    invoke-direct {p0, p1}, Lgov/nist/javax/sip/parser/AddressParametersParser;-><init>(Lgov/nist/javax/sip/parser/Lexer;)V

    return-void
.end method

.method public constructor <init>(Ljava/lang/String;)V
    .locals 0

    .line 1
    invoke-direct {p0, p1}, Lgov/nist/javax/sip/parser/AddressParametersParser;-><init>(Ljava/lang/String;)V

    return-void
.end method


# virtual methods
.method public final parse()Lgov/nist/javax/sip/header/SIPHeader;
    .locals 3

    .line 1
    new-instance v0, Lgov/nist/javax/sip/header/ims/PathList;

    .line 2
    .line 3
    invoke-direct {v0}, Lgov/nist/javax/sip/header/ims/PathList;-><init>()V

    .line 4
    .line 5
    .line 6
    iget-object v1, p0, Lgov/nist/core/ParserCore;->lexer:Lgov/nist/core/LexerCore;

    .line 7
    .line 8
    const/16 v2, 0x847

    .line 9
    .line 10
    invoke-virtual {v1, v2}, Lgov/nist/core/LexerCore;->match(I)Lgov/nist/core/Token;

    .line 11
    .line 12
    .line 13
    iget-object v1, p0, Lgov/nist/core/ParserCore;->lexer:Lgov/nist/core/LexerCore;

    .line 14
    .line 15
    invoke-virtual {v1}, Lgov/nist/core/LexerCore;->SPorHT()V

    .line 16
    .line 17
    .line 18
    iget-object v1, p0, Lgov/nist/core/ParserCore;->lexer:Lgov/nist/core/LexerCore;

    .line 19
    .line 20
    const/16 v2, 0x3a

    .line 21
    .line 22
    invoke-virtual {v1, v2}, Lgov/nist/core/LexerCore;->match(I)Lgov/nist/core/Token;

    .line 23
    .line 24
    .line 25
    iget-object v1, p0, Lgov/nist/core/ParserCore;->lexer:Lgov/nist/core/LexerCore;

    .line 26
    .line 27
    invoke-virtual {v1}, Lgov/nist/core/LexerCore;->SPorHT()V

    .line 28
    .line 29
    .line 30
    :goto_0
    new-instance v1, Lgov/nist/javax/sip/header/ims/Path;

    .line 31
    .line 32
    invoke-direct {v1}, Lgov/nist/javax/sip/header/ims/Path;-><init>()V

    .line 33
    .line 34
    .line 35
    invoke-virtual {p0, v1}, Lgov/nist/javax/sip/parser/AddressParametersParser;->parse(Lgov/nist/javax/sip/header/AddressParametersHeader;)V

    .line 36
    .line 37
    .line 38
    invoke-virtual {v0, v1}, Lgov/nist/javax/sip/header/SIPHeaderList;->add(Lgov/nist/javax/sip/header/SIPHeader;)V

    .line 39
    .line 40
    .line 41
    iget-object v1, p0, Lgov/nist/core/ParserCore;->lexer:Lgov/nist/core/LexerCore;

    .line 42
    .line 43
    invoke-virtual {v1}, Lgov/nist/core/LexerCore;->SPorHT()V

    .line 44
    .line 45
    .line 46
    iget-object v1, p0, Lgov/nist/core/ParserCore;->lexer:Lgov/nist/core/LexerCore;

    .line 47
    .line 48
    const/4 v2, 0x0

    .line 49
    invoke-virtual {v1, v2}, Lgov/nist/core/StringTokenizer;->lookAhead(I)C

    .line 50
    .line 51
    .line 52
    move-result v1

    .line 53
    const/16 v2, 0x2c

    .line 54
    .line 55
    if-ne v1, v2, :cond_0

    .line 56
    .line 57
    iget-object v1, p0, Lgov/nist/core/ParserCore;->lexer:Lgov/nist/core/LexerCore;

    .line 58
    .line 59
    invoke-virtual {v1, v2}, Lgov/nist/core/LexerCore;->match(I)Lgov/nist/core/Token;

    .line 60
    .line 61
    .line 62
    iget-object v1, p0, Lgov/nist/core/ParserCore;->lexer:Lgov/nist/core/LexerCore;

    .line 63
    .line 64
    invoke-virtual {v1}, Lgov/nist/core/LexerCore;->SPorHT()V

    .line 65
    .line 66
    .line 67
    goto :goto_0

    .line 68
    :cond_0
    const/16 v2, 0xa

    .line 69
    .line 70
    if-ne v1, v2, :cond_1

    .line 71
    .line 72
    return-object v0

    .line 73
    :cond_1
    const-string v0, "unexpected char"

    .line 74
    .line 75
    invoke-virtual {p0, v0}, Lgov/nist/javax/sip/parser/Parser;->createParseException(Ljava/lang/String;)Ljava/text/ParseException;

    .line 76
    .line 77
    .line 78
    move-result-object p0

    .line 79
    throw p0
.end method