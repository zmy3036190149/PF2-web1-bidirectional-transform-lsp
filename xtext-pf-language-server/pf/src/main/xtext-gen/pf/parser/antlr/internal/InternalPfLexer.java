package pf.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalPfLexer extends Lexer {
    public static final int RULE_STRING=5;
    public static final int RULE_SL_COMMENT=9;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__37=37;
    public static final int T__16=16;
    public static final int T__38=38;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__33=33;
    public static final int T__12=12;
    public static final int T__34=34;
    public static final int T__13=13;
    public static final int T__35=35;
    public static final int T__14=14;
    public static final int T__36=36;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_QUALIFIED_NAME=6;
    public static final int RULE_ID=4;
    public static final int RULE_WS=10;
    public static final int RULE_ANY_OTHER=11;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=7;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=8;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators

    public InternalPfLexer() {;} 
    public InternalPfLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public InternalPfLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "InternalPf.g"; }

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:11:7: ( 'problem:' )
            // InternalPf.g:11:9: 'problem:'
            {
            match("problem:"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:12:7: ( 'for' )
            // InternalPf.g:12:9: 'for'
            {
            match("for"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:13:7: ( ':' )
            // InternalPf.g:13:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:14:7: ( '{' )
            // InternalPf.g:14:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:15:7: ( ',' )
            // InternalPf.g:15:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:16:7: ( 'see' )
            // InternalPf.g:16:9: 'see'
            {
            match("see"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:17:7: ( 'domain' )
            // InternalPf.g:17:9: 'domain'
            {
            match("domain"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:18:7: ( 'problem' )
            // InternalPf.g:18:9: 'problem'
            {
            match("problem"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:19:7: ( '}' )
            // InternalPf.g:19:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:20:7: ( '!' )
            // InternalPf.g:20:9: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:21:7: ( 'R' )
            // InternalPf.g:21:9: 'R'
            {
            match('R'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:22:7: ( 'M' )
            // InternalPf.g:22:9: 'M'
            {
            match('M'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:23:7: ( 'B' )
            // InternalPf.g:23:9: 'B'
            {
            match('B'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:24:7: ( 'X' )
            // InternalPf.g:24:9: 'X'
            {
            match('X'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:25:7: ( 'C' )
            // InternalPf.g:25:9: 'C'
            {
            match('C'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:26:7: ( 'D' )
            // InternalPf.g:26:9: 'D'
            {
            match('D'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:27:7: ( 'P' )
            // InternalPf.g:27:9: 'P'
            {
            match('P'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:28:7: ( '?' )
            // InternalPf.g:28:9: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:29:7: ( 'phenomenon' )
            // InternalPf.g:29:9: 'phenomenon'
            {
            match("phenomenon"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:30:7: ( 'event' )
            // InternalPf.g:30:9: 'event'
            {
            match("event"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:31:7: ( 'state' )
            // InternalPf.g:31:9: 'state'
            {
            match("state"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:32:7: ( 'value' )
            // InternalPf.g:32:9: 'value'
            {
            match("value"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:33:7: ( '--' )
            // InternalPf.g:33:9: '--'
            {
            match("--"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:34:7: ( '~~' )
            // InternalPf.g:34:9: '~~'
            {
            match("~~"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:35:7: ( '<~' )
            // InternalPf.g:35:9: '<~'
            {
            match("<~"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:36:7: ( '->' )
            // InternalPf.g:36:9: '->'
            {
            match("->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:37:7: ( '~>' )
            // InternalPf.g:37:9: '~>'
            {
            match("~>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "RULE_ID"
    public final void mRULE_ID() throws RecognitionException {
        try {
            int _type = RULE_ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:806:9: ( ( '#' (~ ( '#' ) )+ '#' | ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '(' | ')' )* ) )
            // InternalPf.g:806:11: ( '#' (~ ( '#' ) )+ '#' | ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '(' | ')' )* )
            {
            // InternalPf.g:806:11: ( '#' (~ ( '#' ) )+ '#' | ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '(' | ')' )* )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='#') ) {
                alt4=1;
            }
            else if ( ((LA4_0>='A' && LA4_0<='Z')||(LA4_0>='^' && LA4_0<='_')||(LA4_0>='a' && LA4_0<='z')) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // InternalPf.g:806:12: '#' (~ ( '#' ) )+ '#'
                    {
                    match('#'); 
                    // InternalPf.g:806:16: (~ ( '#' ) )+
                    int cnt1=0;
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( ((LA1_0>='\u0000' && LA1_0<='\"')||(LA1_0>='$' && LA1_0<='\uFFFF')) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // InternalPf.g:806:16: ~ ( '#' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\"')||(input.LA(1)>='$' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt1 >= 1 ) break loop1;
                                EarlyExitException eee =
                                    new EarlyExitException(1, input);
                                throw eee;
                        }
                        cnt1++;
                    } while (true);

                    match('#'); 

                    }
                    break;
                case 2 :
                    // InternalPf.g:806:28: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '(' | ')' )*
                    {
                    // InternalPf.g:806:28: ( '^' )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0=='^') ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // InternalPf.g:806:28: '^'
                            {
                            match('^'); 

                            }
                            break;

                    }

                    if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // InternalPf.g:806:57: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '(' | ')' )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>='(' && LA3_0<=')')||(LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='_'||(LA3_0>='a' && LA3_0<='z')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // InternalPf.g:
                    	    {
                    	    if ( (input.LA(1)>='(' && input.LA(1)<=')')||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ID"

    // $ANTLR start "RULE_QUALIFIED_NAME"
    public final void mRULE_QUALIFIED_NAME() throws RecognitionException {
        try {
            int _type = RULE_QUALIFIED_NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:808:21: ( RULE_ID ( '.' RULE_ID )* )
            // InternalPf.g:808:23: RULE_ID ( '.' RULE_ID )*
            {
            mRULE_ID(); 
            // InternalPf.g:808:31: ( '.' RULE_ID )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='.') ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalPf.g:808:32: '.' RULE_ID
            	    {
            	    match('.'); 
            	    mRULE_ID(); 

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_QUALIFIED_NAME"

    // $ANTLR start "RULE_INT"
    public final void mRULE_INT() throws RecognitionException {
        try {
            int _type = RULE_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:810:10: ( ( '0' .. '9' )+ )
            // InternalPf.g:810:12: ( '0' .. '9' )+
            {
            // InternalPf.g:810:12: ( '0' .. '9' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // InternalPf.g:810:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_INT"

    // $ANTLR start "RULE_STRING"
    public final void mRULE_STRING() throws RecognitionException {
        try {
            int _type = RULE_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:812:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // InternalPf.g:812:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // InternalPf.g:812:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\"') ) {
                alt9=1;
            }
            else if ( (LA9_0=='\'') ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // InternalPf.g:812:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalPf.g:812:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
                    loop7:
                    do {
                        int alt7=3;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0=='\\') ) {
                            alt7=1;
                        }
                        else if ( ((LA7_0>='\u0000' && LA7_0<='!')||(LA7_0>='#' && LA7_0<='[')||(LA7_0>=']' && LA7_0<='\uFFFF')) ) {
                            alt7=2;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // InternalPf.g:812:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalPf.g:812:28: ~ ( ( '\\\\' | '\"' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // InternalPf.g:812:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalPf.g:812:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
                    loop8:
                    do {
                        int alt8=3;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0=='\\') ) {
                            alt8=1;
                        }
                        else if ( ((LA8_0>='\u0000' && LA8_0<='&')||(LA8_0>='(' && LA8_0<='[')||(LA8_0>=']' && LA8_0<='\uFFFF')) ) {
                            alt8=2;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // InternalPf.g:812:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalPf.g:812:61: ~ ( ( '\\\\' | '\\'' ) )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_STRING"

    // $ANTLR start "RULE_ML_COMMENT"
    public final void mRULE_ML_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:814:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalPf.g:814:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalPf.g:814:24: ( options {greedy=false; } : . )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0=='*') ) {
                    int LA10_1 = input.LA(2);

                    if ( (LA10_1=='/') ) {
                        alt10=2;
                    }
                    else if ( ((LA10_1>='\u0000' && LA10_1<='.')||(LA10_1>='0' && LA10_1<='\uFFFF')) ) {
                        alt10=1;
                    }


                }
                else if ( ((LA10_0>='\u0000' && LA10_0<=')')||(LA10_0>='+' && LA10_0<='\uFFFF')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // InternalPf.g:814:52: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ML_COMMENT"

    // $ANTLR start "RULE_SL_COMMENT"
    public final void mRULE_SL_COMMENT() throws RecognitionException {
        try {
            int _type = RULE_SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:816:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalPf.g:816:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalPf.g:816:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='\u0000' && LA11_0<='\t')||(LA11_0>='\u000B' && LA11_0<='\f')||(LA11_0>='\u000E' && LA11_0<='\uFFFF')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalPf.g:816:24: ~ ( ( '\\n' | '\\r' ) )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            // InternalPf.g:816:40: ( ( '\\r' )? '\\n' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0=='\n'||LA13_0=='\r') ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalPf.g:816:41: ( '\\r' )? '\\n'
                    {
                    // InternalPf.g:816:41: ( '\\r' )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0=='\r') ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // InternalPf.g:816:41: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }

                    match('\n'); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_SL_COMMENT"

    // $ANTLR start "RULE_WS"
    public final void mRULE_WS() throws RecognitionException {
        try {
            int _type = RULE_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:818:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalPf.g:818:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalPf.g:818:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>='\t' && LA14_0<='\n')||LA14_0=='\r'||LA14_0==' ') ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // InternalPf.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_WS"

    // $ANTLR start "RULE_ANY_OTHER"
    public final void mRULE_ANY_OTHER() throws RecognitionException {
        try {
            int _type = RULE_ANY_OTHER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // InternalPf.g:820:16: ( . )
            // InternalPf.g:820:18: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RULE_ANY_OTHER"

    public void mTokens() throws RecognitionException {
        // InternalPf.g:1:8: ( T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | RULE_ID | RULE_QUALIFIED_NAME | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER )
        int alt15=35;
        alt15 = dfa15.predict(input);
        switch (alt15) {
            case 1 :
                // InternalPf.g:1:10: T__12
                {
                mT__12(); 

                }
                break;
            case 2 :
                // InternalPf.g:1:16: T__13
                {
                mT__13(); 

                }
                break;
            case 3 :
                // InternalPf.g:1:22: T__14
                {
                mT__14(); 

                }
                break;
            case 4 :
                // InternalPf.g:1:28: T__15
                {
                mT__15(); 

                }
                break;
            case 5 :
                // InternalPf.g:1:34: T__16
                {
                mT__16(); 

                }
                break;
            case 6 :
                // InternalPf.g:1:40: T__17
                {
                mT__17(); 

                }
                break;
            case 7 :
                // InternalPf.g:1:46: T__18
                {
                mT__18(); 

                }
                break;
            case 8 :
                // InternalPf.g:1:52: T__19
                {
                mT__19(); 

                }
                break;
            case 9 :
                // InternalPf.g:1:58: T__20
                {
                mT__20(); 

                }
                break;
            case 10 :
                // InternalPf.g:1:64: T__21
                {
                mT__21(); 

                }
                break;
            case 11 :
                // InternalPf.g:1:70: T__22
                {
                mT__22(); 

                }
                break;
            case 12 :
                // InternalPf.g:1:76: T__23
                {
                mT__23(); 

                }
                break;
            case 13 :
                // InternalPf.g:1:82: T__24
                {
                mT__24(); 

                }
                break;
            case 14 :
                // InternalPf.g:1:88: T__25
                {
                mT__25(); 

                }
                break;
            case 15 :
                // InternalPf.g:1:94: T__26
                {
                mT__26(); 

                }
                break;
            case 16 :
                // InternalPf.g:1:100: T__27
                {
                mT__27(); 

                }
                break;
            case 17 :
                // InternalPf.g:1:106: T__28
                {
                mT__28(); 

                }
                break;
            case 18 :
                // InternalPf.g:1:112: T__29
                {
                mT__29(); 

                }
                break;
            case 19 :
                // InternalPf.g:1:118: T__30
                {
                mT__30(); 

                }
                break;
            case 20 :
                // InternalPf.g:1:124: T__31
                {
                mT__31(); 

                }
                break;
            case 21 :
                // InternalPf.g:1:130: T__32
                {
                mT__32(); 

                }
                break;
            case 22 :
                // InternalPf.g:1:136: T__33
                {
                mT__33(); 

                }
                break;
            case 23 :
                // InternalPf.g:1:142: T__34
                {
                mT__34(); 

                }
                break;
            case 24 :
                // InternalPf.g:1:148: T__35
                {
                mT__35(); 

                }
                break;
            case 25 :
                // InternalPf.g:1:154: T__36
                {
                mT__36(); 

                }
                break;
            case 26 :
                // InternalPf.g:1:160: T__37
                {
                mT__37(); 

                }
                break;
            case 27 :
                // InternalPf.g:1:166: T__38
                {
                mT__38(); 

                }
                break;
            case 28 :
                // InternalPf.g:1:172: RULE_ID
                {
                mRULE_ID(); 

                }
                break;
            case 29 :
                // InternalPf.g:1:180: RULE_QUALIFIED_NAME
                {
                mRULE_QUALIFIED_NAME(); 

                }
                break;
            case 30 :
                // InternalPf.g:1:200: RULE_INT
                {
                mRULE_INT(); 

                }
                break;
            case 31 :
                // InternalPf.g:1:209: RULE_STRING
                {
                mRULE_STRING(); 

                }
                break;
            case 32 :
                // InternalPf.g:1:221: RULE_ML_COMMENT
                {
                mRULE_ML_COMMENT(); 

                }
                break;
            case 33 :
                // InternalPf.g:1:237: RULE_SL_COMMENT
                {
                mRULE_SL_COMMENT(); 

                }
                break;
            case 34 :
                // InternalPf.g:1:253: RULE_WS
                {
                mRULE_WS(); 

                }
                break;
            case 35 :
                // InternalPf.g:1:261: RULE_ANY_OTHER
                {
                mRULE_ANY_OTHER(); 

                }
                break;

        }

    }


    protected DFA15 dfa15 = new DFA15(this);
    static final String DFA15_eotS =
        "\1\uffff\2\43\3\uffff\2\43\2\uffff\1\56\1\57\1\60\1\61\1\62\1\63\1\64\1\uffff\2\43\5\37\1\43\1\uffff\3\37\2\uffff\3\43\2\uffff\1\43\3\uffff\3\43\12\uffff\2\43\6\uffff\1\43\5\uffff\2\43\1\117\1\120\7\43\2\uffff\6\43\1\135\1\43\1\137\1\140\2\43\1\uffff\1\143\2\uffff\1\145\1\43\3\uffff\2\43\1\151\1\uffff";
    static final String DFA15_eofS =
        "\152\uffff";
    static final String DFA15_minS =
        "\1\0\2\50\3\uffff\2\50\2\uffff\7\50\1\uffff\2\50\1\55\1\76\1\176\1\0\1\101\1\50\1\uffff\2\0\1\52\2\uffff\3\50\2\uffff\1\50\3\uffff\3\50\12\uffff\2\50\5\uffff\1\0\1\50\5\uffff\10\50\1\56\2\50\2\uffff\14\50\1\uffff\1\50\2\uffff\2\50\3\uffff\3\50\1\uffff";
    static final String DFA15_maxS =
        "\1\uffff\2\172\3\uffff\2\172\2\uffff\7\172\1\uffff\2\172\1\76\2\176\1\uffff\2\172\1\uffff\2\uffff\1\57\2\uffff\3\172\2\uffff\1\172\3\uffff\3\172\12\uffff\2\172\5\uffff\1\uffff\1\172\5\uffff\10\172\1\56\2\172\2\uffff\14\172\1\uffff\1\172\2\uffff\2\172\3\uffff\3\172\1\uffff";
    static final String DFA15_acceptS =
        "\3\uffff\1\3\1\4\1\5\2\uffff\1\11\1\12\7\uffff\1\22\10\uffff\1\36\3\uffff\1\42\1\43\3\uffff\1\34\1\35\1\uffff\1\3\1\4\1\5\3\uffff\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\2\uffff\1\27\1\32\1\30\1\33\1\31\2\uffff\1\36\1\37\1\40\1\41\1\42\13\uffff\1\2\1\6\14\uffff\1\25\1\uffff\1\24\1\26\2\uffff\1\7\1\1\1\10\3\uffff\1\23";
    static final String DFA15_specialS =
        "\1\4\26\uffff\1\0\3\uffff\1\3\1\1\40\uffff\1\2\54\uffff}>";
    static final String[] DFA15_transitionS = {
            "\11\37\2\36\2\37\1\36\22\37\1\36\1\11\1\33\1\27\3\37\1\34\4\37\1\5\1\24\1\37\1\35\12\32\1\3\1\37\1\26\2\37\1\21\1\37\1\31\1\14\1\16\1\17\10\31\1\13\2\31\1\20\1\31\1\12\5\31\1\15\2\31\3\37\1\30\1\31\1\37\3\31\1\7\1\22\1\2\11\31\1\1\2\31\1\6\2\31\1\23\4\31\1\4\1\37\1\10\1\25\uff81\37",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\7\42\1\41\11\42\1\40\10\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\16\42\1\45\13\42",
            "",
            "",
            "",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\4\42\1\51\16\42\1\52\6\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\16\42\1\53\13\42",
            "",
            "",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\25\42\1\66\4\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\1\67\31\42",
            "\1\70\20\uffff\1\71",
            "\1\73\77\uffff\1\72",
            "\1\74",
            "\43\75\1\uffff\uffdc\75",
            "\32\76\4\uffff\1\76\1\uffff\32\76",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "\0\100",
            "\0\100",
            "\1\101\4\uffff\1\102",
            "",
            "",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\16\42\1\104\13\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\4\42\1\105\25\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\21\42\1\106\10\42",
            "",
            "",
            "",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\4\42\1\107\25\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\1\110\31\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\14\42\1\111\15\42",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\4\42\1\112\25\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\13\42\1\113\16\42",
            "",
            "",
            "",
            "",
            "",
            "\43\75\1\114\uffdc\75",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "",
            "",
            "",
            "",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\1\42\1\115\30\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\15\42\1\116\14\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\23\42\1\121\6\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\1\122\31\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\15\42\1\123\14\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\24\42\1\124\5\42",
            "\1\44",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\13\42\1\125\16\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\16\42\1\126\13\42",
            "",
            "",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\4\42\1\127\25\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\10\42\1\130\21\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\23\42\1\131\6\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\4\42\1\132\25\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\4\42\1\133\25\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\14\42\1\134\15\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\15\42\1\136\14\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\14\42\1\141\15\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\4\42\1\142\25\42",
            "",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "",
            "",
            "\2\42\4\uffff\1\44\1\uffff\12\42\1\144\6\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\15\42\1\146\14\42",
            "",
            "",
            "",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\16\42\1\147\13\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\15\42\1\150\14\42",
            "\2\42\4\uffff\1\44\1\uffff\12\42\7\uffff\32\42\4\uffff\1\42\1\uffff\32\42",
            ""
    };

    static final short[] DFA15_eot = DFA.unpackEncodedString(DFA15_eotS);
    static final short[] DFA15_eof = DFA.unpackEncodedString(DFA15_eofS);
    static final char[] DFA15_min = DFA.unpackEncodedStringToUnsignedChars(DFA15_minS);
    static final char[] DFA15_max = DFA.unpackEncodedStringToUnsignedChars(DFA15_maxS);
    static final short[] DFA15_accept = DFA.unpackEncodedString(DFA15_acceptS);
    static final short[] DFA15_special = DFA.unpackEncodedString(DFA15_specialS);
    static final short[][] DFA15_transition;

    static {
        int numStates = DFA15_transitionS.length;
        DFA15_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA15_transition[i] = DFA.unpackEncodedString(DFA15_transitionS[i]);
        }
    }

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = DFA15_eot;
            this.eof = DFA15_eof;
            this.min = DFA15_min;
            this.max = DFA15_max;
            this.accept = DFA15_accept;
            this.special = DFA15_special;
            this.transition = DFA15_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | RULE_ID | RULE_QUALIFIED_NAME | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA15_23 = input.LA(1);

                        s = -1;
                        if ( ((LA15_23>='\u0000' && LA15_23<='\"')||(LA15_23>='$' && LA15_23<='\uFFFF')) ) {s = 61;}

                        else s = 31;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA15_28 = input.LA(1);

                        s = -1;
                        if ( ((LA15_28>='\u0000' && LA15_28<='\uFFFF')) ) {s = 64;}

                        else s = 31;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA15_61 = input.LA(1);

                        s = -1;
                        if ( (LA15_61=='#') ) {s = 76;}

                        else if ( ((LA15_61>='\u0000' && LA15_61<='\"')||(LA15_61>='$' && LA15_61<='\uFFFF')) ) {s = 61;}

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA15_27 = input.LA(1);

                        s = -1;
                        if ( ((LA15_27>='\u0000' && LA15_27<='\uFFFF')) ) {s = 64;}

                        else s = 31;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA15_0 = input.LA(1);

                        s = -1;
                        if ( (LA15_0=='p') ) {s = 1;}

                        else if ( (LA15_0=='f') ) {s = 2;}

                        else if ( (LA15_0==':') ) {s = 3;}

                        else if ( (LA15_0=='{') ) {s = 4;}

                        else if ( (LA15_0==',') ) {s = 5;}

                        else if ( (LA15_0=='s') ) {s = 6;}

                        else if ( (LA15_0=='d') ) {s = 7;}

                        else if ( (LA15_0=='}') ) {s = 8;}

                        else if ( (LA15_0=='!') ) {s = 9;}

                        else if ( (LA15_0=='R') ) {s = 10;}

                        else if ( (LA15_0=='M') ) {s = 11;}

                        else if ( (LA15_0=='B') ) {s = 12;}

                        else if ( (LA15_0=='X') ) {s = 13;}

                        else if ( (LA15_0=='C') ) {s = 14;}

                        else if ( (LA15_0=='D') ) {s = 15;}

                        else if ( (LA15_0=='P') ) {s = 16;}

                        else if ( (LA15_0=='?') ) {s = 17;}

                        else if ( (LA15_0=='e') ) {s = 18;}

                        else if ( (LA15_0=='v') ) {s = 19;}

                        else if ( (LA15_0=='-') ) {s = 20;}

                        else if ( (LA15_0=='~') ) {s = 21;}

                        else if ( (LA15_0=='<') ) {s = 22;}

                        else if ( (LA15_0=='#') ) {s = 23;}

                        else if ( (LA15_0=='^') ) {s = 24;}

                        else if ( (LA15_0=='A'||(LA15_0>='E' && LA15_0<='L')||(LA15_0>='N' && LA15_0<='O')||LA15_0=='Q'||(LA15_0>='S' && LA15_0<='W')||(LA15_0>='Y' && LA15_0<='Z')||LA15_0=='_'||(LA15_0>='a' && LA15_0<='c')||(LA15_0>='g' && LA15_0<='o')||(LA15_0>='q' && LA15_0<='r')||(LA15_0>='t' && LA15_0<='u')||(LA15_0>='w' && LA15_0<='z')) ) {s = 25;}

                        else if ( ((LA15_0>='0' && LA15_0<='9')) ) {s = 26;}

                        else if ( (LA15_0=='\"') ) {s = 27;}

                        else if ( (LA15_0=='\'') ) {s = 28;}

                        else if ( (LA15_0=='/') ) {s = 29;}

                        else if ( ((LA15_0>='\t' && LA15_0<='\n')||LA15_0=='\r'||LA15_0==' ') ) {s = 30;}

                        else if ( ((LA15_0>='\u0000' && LA15_0<='\b')||(LA15_0>='\u000B' && LA15_0<='\f')||(LA15_0>='\u000E' && LA15_0<='\u001F')||(LA15_0>='$' && LA15_0<='&')||(LA15_0>='(' && LA15_0<='+')||LA15_0=='.'||LA15_0==';'||(LA15_0>='=' && LA15_0<='>')||LA15_0=='@'||(LA15_0>='[' && LA15_0<=']')||LA15_0=='`'||LA15_0=='|'||(LA15_0>='\u007F' && LA15_0<='\uFFFF')) ) {s = 31;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 15, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}