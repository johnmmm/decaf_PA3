//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short COMPLEX=262;
public final static short NULL=263;
public final static short EXTENDS=264;
public final static short THIS=265;
public final static short WHILE=266;
public final static short FOR=267;
public final static short IF=268;
public final static short ELSE=269;
public final static short RETURN=270;
public final static short BREAK=271;
public final static short NEW=272;
public final static short PRINT=273;
public final static short READ_INTEGER=274;
public final static short READ_LINE=275;
public final static short LITERAL=276;
public final static short IDENTIFIER=277;
public final static short AND=278;
public final static short OR=279;
public final static short STATIC=280;
public final static short INSTANCEOF=281;
public final static short LESS_EQUAL=282;
public final static short GREATER_EQUAL=283;
public final static short EQUAL=284;
public final static short NOT_EQUAL=285;
public final static short CASE=286;
public final static short DEFAULT=287;
public final static short SUPER=288;
public final static short DCOPY=289;
public final static short SCOPY=290;
public final static short RE=291;
public final static short IM=292;
public final static short DO=293;
public final static short OD=294;
public final static short UMINUS=295;
public final static short EMPTY=296;
public final static short ContinueStmt=297;
public final static short RepeatStmt=298;
public final static short SwitchStmt=299;
public final static short COMPCAST=300;
public final static short DOBLOCK=301;
public final static short PRINTCOMP=302;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   14,   14,   14,   26,   26,   23,   23,   25,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   28,   28,   29,   27,   27,   31,   31,   16,
   17,   20,   30,   32,   32,   34,   33,   22,   35,   35,
   36,   15,   37,   37,   18,   18,   19,   21,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    2,    2,    1,    1,    1,
    2,    3,    1,    0,    2,    0,    2,    4,    5,    1,
    1,    1,    1,    1,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    2,    2,
    3,    3,    1,    4,    5,    6,    5,    4,    4,    2,
    2,    2,    1,    1,    1,    1,    0,    3,    1,    5,
    9,    1,    8,    2,    0,    4,    4,    3,    3,    1,
    3,    6,    2,    0,    2,    1,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    6,    9,    0,   10,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   84,   73,    0,    0,
    0,    0,   92,    0,    0,    0,    0,   83,    0,    0,
    0,    0,   25,    0,   85,    0,    0,    0,    0,    0,
    0,   38,   39,    0,    0,   28,   40,   26,    0,   30,
   31,   32,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   52,   53,   54,    0,    0,    0,   50,    0,   51,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  100,   36,    0,
    0,   29,   33,   34,   35,   37,   41,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   45,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   71,   72,    0,    0,   68,    0,    0,    0,    0,
   98,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   74,    0,    0,  107,    0,    0,    0,   78,
   79,  101,   99,  108,   48,    0,    0,   90,    0,    0,
   75,    0,    0,   77,   95,   49,    0,    0,  102,   76,
    0,    0,  103,    0,    0,    0,   94,    0,    0,    0,
   93,   91,    0,    0,   97,   96,
};
final static short yydgoto[] = {                          2,
    3,    4,   76,   21,   34,    8,   11,   23,   35,   36,
   77,   46,   78,   79,   80,   81,   82,   83,   84,   85,
   86,   87,   98,   89,  100,   91,  196,   92,   93,   94,
  151,  211,  216,  217,  117,  118,  209,
};
final static short yysindex[] = {                      -238,
 -242,    0, -238,    0, -214,    0, -211,  -51,    0,    0,
 -108,    0,    0,    0,    0, -196,    0,  -53,    0,    0,
   35,  -87,    0,    0,  -85,    0,   55,    5,   57,  -53,
    0,  -53,    0,  -83,   61,   64,   62,    0,  -22,  -53,
  -22,    0,    0,    0,    0,   13,    0,    0,   70,   71,
   73,  144,    0,  -59,   75,   78,   79,    0,   85,  144,
  144,  100,    0,   92,    0,   97,  101,  144,  144,  144,
   45,    0,    0,  144,  102,    0,    0,    0,   88,    0,
    0,    0,   89,   98,  104,  105,  106,   82,  981,    0,
 -161,    0,    0,    0,  144,  144,  144,    0,  981,    0,
  116,   69,  144,  125,  127,  144,  -26,  -26, -107,  555,
  144,  144,  144,  981,  981,  566, -258,    0,    0,  981,
  144,    0,    0,    0,    0,    0,    0,  144,  144,  144,
  144,  144,  144,  144,  144,  144,  144,  144,  144,  144,
  144,    0,  144,  131,  588,  114,  630,  133,  122,  981,
    4,    0,    0,  656,  135,    0,  755,  815,  837,   60,
    0,  144,    8,  981,   84, 1016,   -5,   -5,  -32,  -32,
   37,   37,  -26,  -26,  -26,   -5,   -5,  858,  144,   60,
  144,   60,    0,  884,  144,    0,  -99,  144,   56,    0,
    0,    0,    0,    0,    0,  139,  137,    0,  910,  -84,
    0,  981,  141,    0,    0,    0,  144,   60,    0,    0,
 -207,  147,    0,  154,  155,   66,    0,   60,  144,  144,
    0,    0,  921,  945,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  195,    0,   91,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  157,    0,    0,  176,
    0,  176,    0,    0,    0,  180,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -57,    0,    0,    0,    0,    0,    0,    0,  -55,
  -55,  -55,    0,    0,    0,    0,    0,  -55,  -55,  -55,
    0,    0,    0,  -55,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, 1005,    0,  528,
    0,    0,    0,    0,  -55,  -58,  -55,    0,  164,    0,
    0,    0,  -55,    0,    0,  -55,  430,  457,    0,    0,
  -55,  -55,  -55,    3,   76,    0,    0,    0,    0,  152,
  -55,    0,    0,    0,    0,    0,    0,  -55,  -55,  -55,
  -55,  -55,  -55,  -55,  -55,  -55,  -55,  -55,  -55,  -55,
  -55,    0,  -55,  404,    0,    0,    0,    0,  -55,   10,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -58,
    0,  -55,    0,  -20,   19,  -25,  715,  866,   29,   65,
 1025, 1065,  466,  493,  502,  962, 1033,    0,  -14,  -58,
  -55,  -58,    0,    0,  -55,    0,    0,  -55,    0,    0,
    0,    0,    0,    0,    0,    0,  190,    0,    0,  -33,
    0,   41,    0,    0,    0,    0,   14,  -58,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -58,  -55,  -55,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  233,  235,   59,   53,    0,    0,    0,  215,    0,
  -17,    0, -151,  -93,    0,    0,    0,    0,    0,    0,
    0,    0,  -21, 1261,  545,    0,    0,   38,    0,    0,
 -103,    0,    0,    0,    0,   90,    0,
};
final static int YYTABLESIZE=1481;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        104,
   44,  106,  146,   28,  139,   28,  104,   28,  192,  137,
  135,  104,  136,  142,  138,   67,   19,  163,   67,  142,
   42,   43,    1,   45,   88,  104,   87,  141,  198,  140,
  200,  139,   67,   67,    5,  161,  137,  135,   42,  136,
  142,  138,  162,   80,  186,   61,   80,  185,  194,    7,
   89,  185,   62,   89,   44,   47,  213,   60,  143,   66,
   80,   80,   66,   22,  143,    9,  222,   67,   58,   60,
   25,   10,   60,  139,   88,  197,   66,   66,  137,  214,
   24,   88,  142,  138,   88,  143,   60,   60,   33,  104,
   33,  104,   61,   26,   30,   80,   32,   31,   44,   62,
   42,   39,   41,  119,   60,   61,  102,   40,   61,   95,
   96,   66,   97,  212,  103,  144,   81,  104,  105,   81,
  139,   60,   61,   61,  106,  137,  135,  143,  136,  142,
  138,  111,   61,   81,   81,   42,  112,   63,   88,   62,
  113,  121,  128,  141,   60,  140,  122,  123,   12,   13,
   14,   15,   16,   17,   61,  148,  124,   61,   88,  149,
   88,   62,  125,  126,  127,  152,   60,  153,   81,  155,
  179,   18,  181,  183,  143,  188,   61,  203,  205,  206,
  185,  210,   42,   62,  208,   88,   88,  218,   60,   27,
  221,   29,   82,   38,    1,   82,   88,   12,   13,   14,
   15,   16,   17,   12,   13,   14,   15,   16,   17,   82,
   82,  219,  220,   15,   31,    5,   20,  101,   46,   46,
   19,   46,  105,  104,  104,  104,  104,  104,  104,  104,
   86,  104,  104,  104,  104,    6,  104,  104,  104,  104,
  104,  104,  104,  104,   82,   20,   37,  104,  215,  131,
  132,  193,  104,   67,  104,  104,  104,  104,  104,  104,
  104,    0,   46,  104,  104,  104,  104,  104,  104,   12,
   13,   14,   15,   16,   17,   47,    0,   48,   49,   50,
   51,    0,   52,   53,   54,   55,   56,   57,   58,    0,
   46,    0,    0,   59,    0,    0,   66,   66,   64,    0,
   65,   66,   67,   68,   69,   70,   60,   60,    0,   71,
   72,   73,   74,    0,   75,    0,   12,   13,   14,   15,
   16,   17,   47,    0,   48,   49,   50,   51,    0,   52,
   53,   54,   55,   56,   57,   58,    0,    0,    0,    0,
   59,    0,   61,   61,    0,   64,    0,   65,   66,   67,
   68,   69,   70,    0,    0,    0,   71,   72,   73,   74,
  109,   75,   47,    0,   48,  131,  132,  133,  134,    0,
    0,   54,    0,   56,   57,   58,    0,    0,    0,    0,
   59,    0,    0,    0,   47,   64,   48,   65,   66,   67,
   68,   69,    0,   54,    0,   56,   57,   58,    0,   74,
    0,    0,   59,    0,    0,    0,   47,   64,   48,   65,
   66,   67,   68,   69,    0,   54,    0,   56,   57,   58,
    0,   74,    0,    0,   59,    0,    0,    0,    0,   64,
    0,   65,   66,   67,   68,   69,    0,    0,    0,    0,
   47,    0,    0,   74,   47,   47,   47,   47,   47,   47,
   47,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   47,   47,   47,   47,   47,   69,    0,    0,    0,
   69,   69,   69,   69,   69,    0,   69,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   69,   69,   69,
    0,   69,    0,   70,   47,    0,   47,   70,   70,   70,
   70,   70,   57,   70,    0,    0,   57,   57,   57,   57,
   57,    0,   57,    0,   70,   70,   70,    0,   70,    0,
    0,    0,   69,   57,   57,   57,    0,   57,    0,   58,
    0,    0,    0,   58,   58,   58,   58,   58,   59,   58,
    0,    0,   59,   59,   59,   59,   59,    0,   59,   70,
   58,   58,   58,    0,   58,    0,    0,    0,   57,   59,
   59,   59,    0,   59,   51,    0,    0,    0,   43,   51,
   51,    0,   51,   51,   51,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   58,   43,   51,    0,   51,
   90,  139,    0,    0,   59,  156,  137,  135,    0,  136,
  142,  138,  139,    0,    0,    0,    0,  137,  135,    0,
  136,  142,  138,    0,  141,    0,  140,    0,   51,    0,
    0,    0,    0,  160,  139,  141,    0,  140,  180,  137,
  135,    0,  136,  142,  138,    0,    0,    0,    0,    0,
   90,    0,    0,    0,    0,  143,    0,  141,    0,  140,
    0,    0,    0,    0,    0,    0,  143,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  139,    0,    0,    0,
  182,  137,  135,    0,  136,  142,  138,    0,  143,    0,
    0,   47,   47,    0,    0,   47,   47,   47,   47,  141,
    0,  140,  139,    0,    0,    0,    0,  137,  135,  187,
  136,  142,  138,    0,   90,    0,    0,   69,   69,    0,
    0,   69,   69,   69,   69,  141,    0,  140,    0,    0,
  143,    0,    0,    0,   90,    0,   90,    0,    0,    0,
    0,    0,    0,    0,   70,   70,    0,    0,   70,   70,
   70,   70,    0,   57,   57,    0,  143,   57,   57,   57,
   57,   90,   90,    0,    0,   64,    0,    0,   64,    0,
    0,    0,   90,    0,    0,    0,    0,    0,    0,    0,
   58,   58,   64,   64,   58,   58,   58,   58,    0,   59,
   59,    0,    0,   59,   59,   59,   59,    0,    0,    0,
    0,  139,    0,    0,    0,  189,  137,  135,    0,  136,
  142,  138,    0,    0,    0,   51,   51,   64,    0,   51,
   51,   51,   51,    0,  141,    0,  140,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  129,  130,    0,    0,  131,  132,  133,  134,
    0,    0,    0,  129,  130,  143,    0,  131,  132,  133,
  134,  139,    0,    0,    0,  190,  137,  135,    0,  136,
  142,  138,    0,    0,    0,  129,  130,    0,    0,  131,
  132,  133,  134,  139,  141,    0,  140,  191,  137,  135,
    0,  136,  142,  138,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  139,    0,  141,    0,  140,  137,
  135,    0,  136,  142,  138,  143,   65,  129,  130,   65,
    0,  131,  132,  133,  134,    0,    0,  141,    0,  140,
  139,    0,    0,   65,   65,  137,  135,  143,  136,  142,
  138,    0,    0,  129,  130,    0,    0,  131,  132,  133,
  134,    0,    0,  141,    0,  140,  139,    0,  143,    0,
  195,  137,  135,    0,  136,  142,  138,  139,   65,    0,
    0,    0,  137,  135,    0,  136,  142,  138,  207,  141,
    0,  140,    0,    0,  143,    0,  201,    0,    0,  225,
  141,  139,  140,    0,    0,    0,  137,  135,    0,  136,
  142,  138,   64,   64,    0,    0,    0,    0,   64,   64,
  143,    0,   63,  226,  141,   63,  140,    0,    0,    0,
    0,  143,    0,    0,    0,    0,    0,  139,    0,   63,
   63,    0,  137,  135,    0,  136,  142,  138,    0,    0,
    0,    0,  129,  130,    0,  143,  131,  132,  133,  134,
  141,   50,  140,    0,    0,    0,   50,   50,    0,   50,
   50,   50,  139,    0,   63,    0,    0,  137,  135,    0,
  136,  142,  138,    0,   50,   55,   50,   55,   55,   55,
    0,  143,    0,   62,    0,  141,   62,  140,    0,    0,
    0,    0,   55,   55,   55,    0,   55,    0,    0,    0,
   62,   62,  129,  130,    0,   50,  131,  132,  133,  134,
    0,    0,    0,    0,    0,   56,  143,   56,   56,   56,
    0,    0,    0,    0,  129,  130,    0,   55,  131,  132,
  133,  134,   56,   56,   56,   62,   56,    0,    0,    0,
    0,    0,    0,    0,    0,  129,  130,    0,    0,  131,
  132,  133,  134,   65,   65,    0,    0,    0,    0,   65,
   65,    0,    0,    0,    0,    0,    0,   56,    0,    0,
    0,  129,  130,    0,    0,  131,  132,  133,  134,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  129,  130,    0,
    0,  131,  132,  133,  134,    0,    0,    0,  129,  130,
    0,    0,  131,  132,  133,  134,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  129,  130,    0,    0,  131,  132,  133,  134,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   63,
   63,    0,    0,    0,    0,   63,   63,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  129,  130,
    0,    0,  131,  132,  133,  134,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   50,   50,    0,    0,   50,   50,   50,   50,
    0,    0,    0,  129,    0,    0,    0,  131,  132,  133,
  134,    0,   55,   55,    0,    0,   55,   55,   55,   55,
   62,   62,   99,    0,    0,    0,   62,   62,    0,    0,
  107,  108,  110,    0,    0,    0,    0,    0,  114,  115,
  116,    0,    0,    0,  120,    0,    0,    0,    0,    0,
    0,    0,   56,   56,    0,    0,   56,   56,   56,   56,
    0,    0,    0,    0,    0,  145,    0,  147,    0,    0,
    0,    0,    0,  150,    0,    0,  154,    0,    0,    0,
    0,  157,  158,  159,    0,    0,    0,    0,    0,    0,
    0,  150,    0,    0,    0,    0,    0,    0,  164,  165,
  166,  167,  168,  169,  170,  171,  172,  173,  174,  175,
  176,  177,    0,  178,    0,    0,    0,    0,    0,  184,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  116,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  150,
    0,  199,    0,    0,    0,  202,    0,    0,  204,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  223,
  224,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   96,   91,   37,   91,   40,   91,  160,   42,
   43,   45,   45,   46,   47,   41,  125,  121,   44,   46,
   41,   39,  261,   41,   46,   59,   41,   60,  180,   62,
  182,   37,   58,   59,  277,  294,   42,   43,   59,   45,
   46,   47,  301,   41,   41,   33,   44,   44,   41,  264,
   41,   44,   40,   44,   41,  263,  208,   45,   91,   41,
   58,   59,   44,   11,   91,  277,  218,   93,  276,   41,
   18,  123,   44,   37,   96,  179,   58,   59,   42,  287,
  277,   41,   46,   47,   44,   91,   58,   59,   30,  123,
   32,  125,   33,   59,   40,   93,   40,   93,   40,   40,
  123,   41,   41,   59,   45,   41,   54,   44,   44,   40,
   40,   93,   40,  207,   40,  277,   41,   40,   40,   44,
   37,   93,   58,   59,   40,   42,   43,   91,   45,   46,
   47,   40,   33,   58,   59,  123,   40,  125,  160,   40,
   40,   40,   61,   60,   45,   62,   59,   59,  257,  258,
  259,  260,  261,  262,   33,   40,   59,   93,  180,   91,
  182,   40,   59,   59,   59,   41,   45,   41,   93,  277,
   40,  280,   59,   41,   91,   41,   33,  277,  123,   41,
   44,   41,  123,   40,  269,  207,  208,   41,   45,  277,
  125,  277,   41,  277,    0,   44,  218,  257,  258,  259,
  260,  261,  262,  257,  258,  259,  260,  261,  262,   58,
   59,   58,   58,  123,   93,   59,   41,  277,  277,  277,
   41,  277,   59,  257,  258,  259,  260,  261,  262,  263,
   41,  265,  266,  267,  268,    3,  270,  271,  272,  273,
  274,  275,  276,  277,   93,   11,   32,  281,  211,  282,
  283,  162,  286,  279,  288,  289,  290,  291,  292,  293,
  294,   -1,  277,  297,  298,  299,  300,  301,  302,  257,
  258,  259,  260,  261,  262,  263,   -1,  265,  266,  267,
  268,   -1,  270,  271,  272,  273,  274,  275,  276,   -1,
  277,   -1,   -1,  281,   -1,   -1,  278,  279,  286,   -1,
  288,  289,  290,  291,  292,  293,  278,  279,   -1,  297,
  298,  299,  300,   -1,  302,   -1,  257,  258,  259,  260,
  261,  262,  263,   -1,  265,  266,  267,  268,   -1,  270,
  271,  272,  273,  274,  275,  276,   -1,   -1,   -1,   -1,
  281,   -1,  278,  279,   -1,  286,   -1,  288,  289,  290,
  291,  292,  293,   -1,   -1,   -1,  297,  298,  299,  300,
  261,  302,  263,   -1,  265,  282,  283,  284,  285,   -1,
   -1,  272,   -1,  274,  275,  276,   -1,   -1,   -1,   -1,
  281,   -1,   -1,   -1,  263,  286,  265,  288,  289,  290,
  291,  292,   -1,  272,   -1,  274,  275,  276,   -1,  300,
   -1,   -1,  281,   -1,   -1,   -1,  263,  286,  265,  288,
  289,  290,  291,  292,   -1,  272,   -1,  274,  275,  276,
   -1,  300,   -1,   -1,  281,   -1,   -1,   -1,   -1,  286,
   -1,  288,  289,  290,  291,  292,   -1,   -1,   -1,   -1,
   37,   -1,   -1,  300,   41,   42,   43,   44,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   58,   59,   60,   61,   62,   37,   -1,   -1,   -1,
   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,   60,
   -1,   62,   -1,   37,   91,   -1,   93,   41,   42,   43,
   44,   45,   37,   47,   -1,   -1,   41,   42,   43,   44,
   45,   -1,   47,   -1,   58,   59,   60,   -1,   62,   -1,
   -1,   -1,   93,   58,   59,   60,   -1,   62,   -1,   37,
   -1,   -1,   -1,   41,   42,   43,   44,   45,   37,   47,
   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,   93,
   58,   59,   60,   -1,   62,   -1,   -1,   -1,   93,   58,
   59,   60,   -1,   62,   37,   -1,   -1,   -1,   41,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   93,   59,   60,   -1,   62,
   46,   37,   -1,   -1,   93,   41,   42,   43,   -1,   45,
   46,   47,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,
   45,   46,   47,   -1,   60,   -1,   62,   -1,   91,   -1,
   -1,   -1,   -1,   58,   37,   60,   -1,   62,   41,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,
   96,   -1,   -1,   -1,   -1,   91,   -1,   60,   -1,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   37,   -1,   -1,   -1,
   41,   42,   43,   -1,   45,   46,   47,   -1,   91,   -1,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   60,
   -1,   62,   37,   -1,   -1,   -1,   -1,   42,   43,   44,
   45,   46,   47,   -1,  160,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   60,   -1,   62,   -1,   -1,
   91,   -1,   -1,   -1,  180,   -1,  182,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   -1,  278,  279,   -1,   91,  282,  283,  284,
  285,  207,  208,   -1,   -1,   41,   -1,   -1,   44,   -1,
   -1,   -1,  218,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  278,  279,   58,   59,  282,  283,  284,  285,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,   -1,   -1,   -1,
   -1,   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,  278,  279,   93,   -1,  282,
  283,  284,  285,   -1,   60,   -1,   62,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,   -1,  278,  279,   91,   -1,  282,  283,  284,
  285,   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,
  283,  284,  285,   37,   60,   -1,   62,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   37,   -1,   60,   -1,   62,   42,
   43,   -1,   45,   46,   47,   91,   41,  278,  279,   44,
   -1,  282,  283,  284,  285,   -1,   -1,   60,   -1,   62,
   37,   -1,   -1,   58,   59,   42,   43,   91,   45,   46,
   47,   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,
  285,   -1,   -1,   60,   -1,   62,   37,   -1,   91,   -1,
   93,   42,   43,   -1,   45,   46,   47,   37,   93,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   59,   60,
   -1,   62,   -1,   -1,   91,   -1,   93,   -1,   -1,   59,
   60,   37,   62,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,  278,  279,   -1,   -1,   -1,   -1,  284,  285,
   91,   -1,   41,   59,   60,   44,   62,   -1,   -1,   -1,
   -1,   91,   -1,   -1,   -1,   -1,   -1,   37,   -1,   58,
   59,   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,  278,  279,   -1,   91,  282,  283,  284,  285,
   60,   37,   62,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   37,   -1,   93,   -1,   -1,   42,   43,   -1,
   45,   46,   47,   -1,   60,   41,   62,   43,   44,   45,
   -1,   91,   -1,   41,   -1,   60,   44,   62,   -1,   -1,
   -1,   -1,   58,   59,   60,   -1,   62,   -1,   -1,   -1,
   58,   59,  278,  279,   -1,   91,  282,  283,  284,  285,
   -1,   -1,   -1,   -1,   -1,   41,   91,   43,   44,   45,
   -1,   -1,   -1,   -1,  278,  279,   -1,   93,  282,  283,
  284,  285,   58,   59,   60,   93,   62,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,
  283,  284,  285,  278,  279,   -1,   -1,   -1,   -1,  284,
  285,   -1,   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   -1,   -1,   -1,  278,  279,
   -1,   -1,  282,  283,  284,  285,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,
  279,   -1,   -1,   -1,   -1,  284,  285,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,
   -1,   -1,  282,  283,  284,  285,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,   -1,  278,   -1,   -1,   -1,  282,  283,  284,
  285,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
  278,  279,   52,   -1,   -1,   -1,  284,  285,   -1,   -1,
   60,   61,   62,   -1,   -1,   -1,   -1,   -1,   68,   69,
   70,   -1,   -1,   -1,   74,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,   -1,   -1,   -1,   95,   -1,   97,   -1,   -1,
   -1,   -1,   -1,  103,   -1,   -1,  106,   -1,   -1,   -1,
   -1,  111,  112,  113,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  121,   -1,   -1,   -1,   -1,   -1,   -1,  128,  129,
  130,  131,  132,  133,  134,  135,  136,  137,  138,  139,
  140,  141,   -1,  143,   -1,   -1,   -1,   -1,   -1,  149,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  162,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  179,
   -1,  181,   -1,   -1,   -1,  185,   -1,   -1,  188,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  219,
  220,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=302;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,"'@'",null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","COMPLEX","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN",
"BREAK","NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND",
"OR","STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL",
"CASE","DEFAULT","SUPER","DCOPY","SCOPY","RE","IM","DO","OD","UMINUS","EMPTY",
"ContinueStmt","RepeatStmt","SwitchStmt","COMPCAST","DOBLOCK","PRINTCOMP",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : COMPLEX",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : ContinueStmt ';'",
"Stmt : PrintCompStmt ';'",
"Stmt : RepeatStmt",
"Stmt : SwitchStmt",
"Stmt : StmtBlock",
"Stmt : Do ';'",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : Super",
"Expr : Cases",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Expr : DCOPY '(' Expr ')'",
"Expr : SCOPY '(' Expr ')'",
"Expr : RE Expr",
"Expr : IM Expr",
"Expr : COMPCAST Expr",
"Constant : LITERAL",
"Constant : NULL",
"Super : SUPER",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"Cases : CASE '(' Expr ')' '{' CaseStmtList DefaultStmt '}'",
"CaseStmtList : CaseStmtList CaseStmt",
"CaseStmtList :",
"CaseStmt : Constant ':' Expr ';'",
"DefaultStmt : DEFAULT ':' Expr ';'",
"Do : DO DoStmtList OD",
"DoStmtList : DoStmtList DOBLOCK DoStmt",
"DoStmtList : DoStmt",
"DoStmt : Expr ':' Stmt",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
"PrintCompStmt : PRINTCOMP '(' ExprList ')'",
};

//#line 528 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 736 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 58 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 64 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 68 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 78 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 84 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 88 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 92 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 96 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 100 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.COMPLEX, val_peek(0).loc);
                	}
break;
case 11:
//#line 104 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 12:
//#line 108 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 13:
//#line 114 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 120 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 124 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 130 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 134 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 138 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 146 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 153 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 157 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 164 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 168 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 174 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 180 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 184 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 191 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 196 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 42:
//#line 216 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 43:
//#line 220 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 44:
//#line 224 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 46:
//#line 231 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 47:
//#line 237 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 48:
//#line 244 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 49:
//#line 250 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 50:
//#line 259 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 54:
//#line 266 "Parser.y"
{
                		
                }
break;
case 55:
//#line 270 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 274 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 278 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 282 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 286 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 290 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 294 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 298 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 302 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 306 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 310 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 314 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 318 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 322 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 69:
//#line 326 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 330 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 334 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 72:
//#line 338 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 73:
//#line 342 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 74:
//#line 346 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 75:
//#line 350 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 76:
//#line 354 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 77:
//#line 358 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 78:
//#line 362 "Parser.y"
{
                		yyval.expr = new Tree.Dcopy(val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 79:
//#line 366 "Parser.y"
{
                		yyval.expr = new Tree.Scopy(val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 80:
//#line 370 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.RE, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 81:
//#line 374 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.IM, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 82:
//#line 378 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.COMPCAST, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 83:
//#line 384 "Parser.y"
{
						yyval.constant = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
						yyval.expr = yyval.constant;
					}
break;
case 84:
//#line 389 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 85:
//#line 395 "Parser.y"
{
						yyval.expr = new Tree.Super(val_peek(0).loc);
					}
break;
case 87:
//#line 402 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 88:
//#line 409 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 89:
//#line 413 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 90:
//#line 420 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 91:
//#line 426 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 92:
//#line 432 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 93:
//#line 438 "Parser.y"
{
						yyval.expr = new Tree.Switch(val_peek(5).expr, val_peek(2).caselist, val_peek(1).defa, val_peek(5).loc);
					}
break;
case 94:
//#line 444 "Parser.y"
{
                        yyval.caselist.add(val_peek(0).cas);
                    }
break;
case 95:
//#line 448 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.caselist = new ArrayList<Tree.Case>();
                    }
break;
case 96:
//#line 455 "Parser.y"
{
 						yyval.cas = new Tree.Case(val_peek(3).constant, val_peek(1).expr, val_peek(3).loc);
 					}
break;
case 97:
//#line 461 "Parser.y"
{
                        yyval.defa = new Tree.Default(val_peek(1).expr, val_peek(3).loc);
                    }
break;
case 98:
//#line 466 "Parser.y"
{
						yyval.stmt = new Tree.Doing(val_peek(1).doeslist, val_peek(2).loc);
					}
break;
case 99:
//#line 472 "Parser.y"
{
						yyval.doeslist.add(val_peek(0).does);
					}
break;
case 100:
//#line 476 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.doeslist = new ArrayList<Tree.Do>();
                        yyval.doeslist.add(val_peek(0).does);
                    }
break;
case 101:
//#line 484 "Parser.y"
{
						yyval.does = new Tree.Do(val_peek(2).expr, val_peek(0).stmt, val_peek(2).loc);
					}
break;
case 102:
//#line 490 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 103:
//#line 496 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 104:
//#line 500 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 105:
//#line 506 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 106:
//#line 510 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 107:
//#line 516 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 108:
//#line 522 "Parser.y"
{
						yyval.stmt = new Tree.Printcomp(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1435 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
