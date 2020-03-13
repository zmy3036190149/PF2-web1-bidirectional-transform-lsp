package pf.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;


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
            // InternalPf.g:11:7: ( 'R' )
            // InternalPf.g:11:9: 'R'
            {
            match('R'); 

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
            // InternalPf.g:12:7: ( 'M' )
            // InternalPf.g:12:9: 'M'
            {
            match('M'); 

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
            // InternalPf.g:13:7: ( 'B' )
            // InternalPf.g:13:9: 'B'
            {
            match('B'); 

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
            // InternalPf.g:14:7: ( 'X' )
            // InternalPf.g:14:9: 'X'
            {
            match('X'); 

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
            // InternalPf.g:15:7: ( 'C' )
            // InternalPf.g:15:9: 'C'
            {
            match('C'); 

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
            // InternalPf.g:16:7: ( 'D' )
            // InternalPf.g:16:9: 'D'
            {
            match('D'); 

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
            // InternalPf.g:17:7: ( 'P' )
            // InternalPf.g:17:9: 'P'
            {
            match('P'); 

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
            // InternalPf.g:18:7: ( '?' )
            // InternalPf.g:18:9: '?'
            {
            match('?'); 

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
            // InternalPf.g:19:7: ( 'phenomenon' )
            // InternalPf.g:19:9: 'phenomenon'
            {
            match("phenomenon"); 


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
            // InternalPf.g:20:7: ( 'event' )
            // InternalPf.g:20:9: 'event'
            {
            match("event"); 


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
            // InternalPf.g:21:7: ( 'state' )
            // InternalPf.g:21:9: 'state'
            {
            match("state"); 


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
            // InternalPf.g:22:7: ( 'value' )
            // InternalPf.g:22:9: 'value'
            {
            match("value"); 


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
            // InternalPf.g:23:7: ( '--' )
            // InternalPf.g:23:9: '--'
            {
            match("--"); 


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
            // InternalPf.g:24:7: ( '~~' )
            // InternalPf.g:24:9: '~~'
            {
            match("~~"); 


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
            // InternalPf.g:25:7: ( '<~' )
            // InternalPf.g:25:9: '<~'
            {
            match("<~"); 


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
            // InternalPf.g:26:7: ( '->' )
            // InternalPf.g:26:9: '->'
            {
            match("->"); 


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
            // InternalPf.g:27:7: ( '~>' )
            // InternalPf.g:27:9: '~>'
            {
            match("~>"); 


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
            // InternalPf.g:28:7: ( 'problem:' )
            // InternalPf.g:28:9: 'problem:'
            {
            match("problem:"); 


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
            // InternalPf.g:29:7: ( 'for' )
            // InternalPf.g:29:9: 'for'
            {
            match("for"); 


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
            // InternalPf.g:30:7: ( ':' )
            // InternalPf.g:30:9: ':'
            {
            match(':'); 

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
            // InternalPf.g:31:7: ( '{' )
            // InternalPf.g:31:9: '{'
            {
            match('{'); 

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
            // InternalPf.g:32:7: ( '}' )
            // InternalPf.g:32:9: '}'
            {
            match('}'); 

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
            // InternalPf.g:33:7: ( ',' )
            // InternalPf.g:33:9: ','
            {
            match(','); 

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
            // InternalPf.g:34:7: ( 'see' )
            // InternalPf.g:34:9: 'see'
            {
            match("see"); 


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
            // InternalPf.g:35:7: ( 'domain' )
            // InternalPf.g:35:9: 'domain'
            {
            match("domain"); 


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
            // InternalPf.g:36:7: ( 'problem' )
            // InternalPf.g:36:9: 'problem'
            {
            match("problem"); 


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
            // InternalPf.g:37:7: ( '!' )
            // InternalPf.g:37:9: '!'
            {
            match('!'); 

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
            // InternalPf.g:2021:9: ( ( '#' (~ ( '#' ) )+ '#' | ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '(' | ')' )* ) )
            // InternalPf.g:2021:11: ( '#' (~ ( '#' ) )+ '#' | ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '(' | ')' )* )
            {
            // InternalPf.g:2021:11: ( '#' (~ ( '#' ) )+ '#' | ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '(' | ')' )* )
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
                    // InternalPf.g:2021:12: '#' (~ ( '#' ) )+ '#'
                    {
                    match('#'); 
                    // InternalPf.g:2021:16: (~ ( '#' ) )+
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
                    	    // InternalPf.g:2021:16: ~ ( '#' )
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
                    // InternalPf.g:2021:28: ( '^' )? ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '(' | ')' )*
                    {
                    // InternalPf.g:2021:28: ( '^' )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0=='^') ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // InternalPf.g:2021:28: '^'
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

                    // InternalPf.g:2021:57: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' | '(' | ')' )*
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
            // InternalPf.g:2023:21: ( RULE_ID ( '.' RULE_ID )* )
            // InternalPf.g:2023:23: RULE_ID ( '.' RULE_ID )*
            {
            mRULE_ID(); 
            // InternalPf.g:2023:31: ( '.' RULE_ID )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='.') ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // InternalPf.g:2023:32: '.' RULE_ID
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
            // InternalPf.g:2025:10: ( ( '0' .. '9' )+ )
            // InternalPf.g:2025:12: ( '0' .. '9' )+
            {
            // InternalPf.g:2025:12: ( '0' .. '9' )+
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
            	    // InternalPf.g:2025:13: '0' .. '9'
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
            // InternalPf.g:2027:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' ) )
            // InternalPf.g:2027:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
            {
            // InternalPf.g:2027:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"' | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\'' )
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
                    // InternalPf.g:2027:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* '\"'
                    {
                    match('\"'); 
                    // InternalPf.g:2027:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
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
                    	    // InternalPf.g:2027:21: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalPf.g:2027:28: ~ ( ( '\\\\' | '\"' ) )
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
                    // InternalPf.g:2027:48: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* '\\''
                    {
                    match('\''); 
                    // InternalPf.g:2027:53: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
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
                    	    // InternalPf.g:2027:54: '\\\\' .
                    	    {
                    	    match('\\'); 
                    	    matchAny(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalPf.g:2027:61: ~ ( ( '\\\\' | '\\'' ) )
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
            // InternalPf.g:2029:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // InternalPf.g:2029:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // InternalPf.g:2029:24: ( options {greedy=false; } : . )*
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
            	    // InternalPf.g:2029:52: .
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
            // InternalPf.g:2031:17: ( '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )? )
            // InternalPf.g:2031:19: '//' (~ ( ( '\\n' | '\\r' ) ) )* ( ( '\\r' )? '\\n' )?
            {
            match("//"); 

            // InternalPf.g:2031:24: (~ ( ( '\\n' | '\\r' ) ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='\u0000' && LA11_0<='\t')||(LA11_0>='\u000B' && LA11_0<='\f')||(LA11_0>='\u000E' && LA11_0<='\uFFFF')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalPf.g:2031:24: ~ ( ( '\\n' | '\\r' ) )
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

            // InternalPf.g:2031:40: ( ( '\\r' )? '\\n' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0=='\n'||LA13_0=='\r') ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalPf.g:2031:41: ( '\\r' )? '\\n'
                    {
                    // InternalPf.g:2031:41: ( '\\r' )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0=='\r') ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // InternalPf.g:2031:41: '\\r'
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
            // InternalPf.g:2033:9: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
            // InternalPf.g:2033:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
            {
            // InternalPf.g:2033:11: ( ' ' | '\\t' | '\\r' | '\\n' )+
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
            // InternalPf.g:2035:16: ( . )
            // InternalPf.g:2035:18: .
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
        "\1\uffff\1\41\1\43\1\44\1\45\1\46\1\47\1\50\1\uffff\4\54\3\37\1\54\4\uffff\1\54\1\uffff\2\37\1\54\1\uffff\3\37\2\uffff\1\54\11\uffff\2\54\1\uffff\4\54\5\uffff\1\54\4\uffff\1\54\2\uffff\1\54\5\uffff\4\54\1\121\1\54\1\123\6\54\1\uffff\1\54\1\uffff\3\54\1\135\1\136\1\137\3\54\3\uffff\1\143\1\54\1\146\1\uffff\1\54\2\uffff\1\54\1\151\1\uffff";
    static final String DFA15_eofS =
        "\152\uffff";
    static final String DFA15_minS =
        "\1\0\7\50\1\uffff\4\50\1\55\1\76\1\176\1\50\4\uffff\1\50\1\uffff\1\0\1\101\1\50\1\uffff\2\0\1\52\2\uffff\1\50\11\uffff\2\50\1\uffff\4\50\5\uffff\1\50\4\uffff\1\50\1\uffff\1\0\1\50\5\uffff\10\50\1\56\4\50\1\uffff\1\50\1\uffff\11\50\3\uffff\3\50\1\uffff\1\50\2\uffff\2\50\1\uffff";
    static final String DFA15_maxS =
        "\1\uffff\7\172\1\uffff\4\172\1\76\2\176\1\172\4\uffff\1\172\1\uffff\1\uffff\2\172\1\uffff\2\uffff\1\57\2\uffff\1\172\11\uffff\2\172\1\uffff\4\172\5\uffff\1\172\4\uffff\1\172\1\uffff\1\uffff\1\172\5\uffff\10\172\1\56\4\172\1\uffff\1\172\1\uffff\11\172\3\uffff\3\172\1\uffff\1\172\2\uffff\2\172\1\uffff";
    static final String DFA15_acceptS =
        "\10\uffff\1\10\10\uffff\1\24\1\25\1\26\1\27\1\uffff\1\33\3\uffff\1\36\3\uffff\1\42\1\43\1\uffff\1\1\1\35\1\2\1\3\1\4\1\5\1\6\1\7\1\10\2\uffff\1\34\4\uffff\1\15\1\20\1\16\1\21\1\17\1\uffff\1\24\1\25\1\26\1\27\1\uffff\1\33\2\uffff\1\36\1\37\1\40\1\41\1\42\15\uffff\1\30\1\uffff\1\23\11\uffff\1\12\1\13\1\14\3\uffff\1\31\1\uffff\1\22\1\32\2\uffff\1\11";
    static final String DFA15_specialS =
        "\1\4\26\uffff\1\0\3\uffff\1\3\1\1\40\uffff\1\2\54\uffff}>";
    static final String[] DFA15_transitionS = {
            "\11\37\2\36\2\37\1\36\22\37\1\36\1\26\1\33\1\27\3\37\1\34\4\37\1\24\1\15\1\37\1\35\12\32\1\21\1\37\1\17\2\37\1\10\1\37\1\31\1\3\1\5\1\6\10\31\1\2\2\31\1\7\1\31\1\1\5\31\1\4\2\31\3\37\1\30\1\31\1\37\3\31\1\25\1\12\1\20\11\31\1\11\2\31\1\13\2\31\1\14\4\31\1\22\1\37\1\23\1\16\uff81\37",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\7\40\1\52\11\40\1\53\10\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\25\40\1\55\4\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\4\40\1\57\16\40\1\56\6\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\1\60\31\40",
            "\1\61\20\uffff\1\62",
            "\1\64\77\uffff\1\63",
            "\1\65",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\16\40\1\66\13\40",
            "",
            "",
            "",
            "",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\16\40\1\73\13\40",
            "",
            "\43\75\1\uffff\uffdc\75",
            "\32\76\4\uffff\1\76\1\uffff\32\76",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "\0\100",
            "\0\100",
            "\1\101\4\uffff\1\102",
            "",
            "",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\4\40\1\104\25\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\16\40\1\105\13\40",
            "",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\4\40\1\106\25\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\1\107\31\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\4\40\1\110\25\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\13\40\1\111\16\40",
            "",
            "",
            "",
            "",
            "",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\21\40\1\112\10\40",
            "",
            "",
            "",
            "",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\14\40\1\113\15\40",
            "",
            "\43\75\1\114\uffdc\75",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "",
            "",
            "",
            "",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\15\40\1\115\14\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\1\40\1\116\30\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\15\40\1\117\14\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\23\40\1\120\6\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\24\40\1\122\5\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\1\124\31\40",
            "\1\42",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\16\40\1\125\13\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\13\40\1\126\16\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\23\40\1\127\6\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\4\40\1\130\25\40",
            "",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\4\40\1\131\25\40",
            "",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\10\40\1\132\21\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\14\40\1\133\15\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\4\40\1\134\25\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\15\40\1\140\14\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\4\40\1\141\25\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\14\40\1\142\15\40",
            "",
            "",
            "",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\15\40\1\144\14\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\1\145\6\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
            "",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\16\40\1\147\13\40",
            "",
            "",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\15\40\1\150\14\40",
            "\2\40\4\uffff\1\42\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32\40",
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
                        if ( (LA15_0=='R') ) {s = 1;}

                        else if ( (LA15_0=='M') ) {s = 2;}

                        else if ( (LA15_0=='B') ) {s = 3;}

                        else if ( (LA15_0=='X') ) {s = 4;}

                        else if ( (LA15_0=='C') ) {s = 5;}

                        else if ( (LA15_0=='D') ) {s = 6;}

                        else if ( (LA15_0=='P') ) {s = 7;}

                        else if ( (LA15_0=='?') ) {s = 8;}

                        else if ( (LA15_0=='p') ) {s = 9;}

                        else if ( (LA15_0=='e') ) {s = 10;}

                        else if ( (LA15_0=='s') ) {s = 11;}

                        else if ( (LA15_0=='v') ) {s = 12;}

                        else if ( (LA15_0=='-') ) {s = 13;}

                        else if ( (LA15_0=='~') ) {s = 14;}

                        else if ( (LA15_0=='<') ) {s = 15;}

                        else if ( (LA15_0=='f') ) {s = 16;}

                        else if ( (LA15_0==':') ) {s = 17;}

                        else if ( (LA15_0=='{') ) {s = 18;}

                        else if ( (LA15_0=='}') ) {s = 19;}

                        else if ( (LA15_0==',') ) {s = 20;}

                        else if ( (LA15_0=='d') ) {s = 21;}

                        else if ( (LA15_0=='!') ) {s = 22;}

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