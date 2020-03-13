package pf.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import pf.services.PfGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalPfParser extends AbstractInternalContentAssistParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_QUALIFIED_NAME", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'R'", "'M'", "'B'", "'X'", "'C'", "'D'", "'P'", "'?'", "'phenomenon'", "'event'", "'state'", "'value'", "'--'", "'~~'", "'<~'", "'->'", "'~>'", "'problem:'", "'for'", "':'", "'{'", "'}'", "','", "'see'", "'domain'", "'problem'", "'!'"
    };
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


        public InternalPfParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalPfParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalPfParser.tokenNames; }
    public String getGrammarFileName() { return "InternalPf.g"; }


    	private PfGrammarAccess grammarAccess;

    	public void setGrammarAccess(PfGrammarAccess grammarAccess) {
    		this.grammarAccess = grammarAccess;
    	}

    	@Override
    	protected Grammar getGrammar() {
    		return grammarAccess.getGrammar();
    	}

    	@Override
    	protected String getValueForTokenName(String tokenName) {
    		return tokenName;
    	}



    // $ANTLR start "entryRuleProblemDiagram"
    // InternalPf.g:53:1: entryRuleProblemDiagram : ruleProblemDiagram EOF ;
    public final void entryRuleProblemDiagram() throws RecognitionException {
        try {
            // InternalPf.g:54:1: ( ruleProblemDiagram EOF )
            // InternalPf.g:55:1: ruleProblemDiagram EOF
            {
             before(grammarAccess.getProblemDiagramRule()); 
            pushFollow(FOLLOW_1);
            ruleProblemDiagram();

            state._fsp--;

             after(grammarAccess.getProblemDiagramRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleProblemDiagram"


    // $ANTLR start "ruleProblemDiagram"
    // InternalPf.g:62:1: ruleProblemDiagram : ( ( rule__ProblemDiagram__Group__0 ) ) ;
    public final void ruleProblemDiagram() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:66:2: ( ( ( rule__ProblemDiagram__Group__0 ) ) )
            // InternalPf.g:67:2: ( ( rule__ProblemDiagram__Group__0 ) )
            {
            // InternalPf.g:67:2: ( ( rule__ProblemDiagram__Group__0 ) )
            // InternalPf.g:68:3: ( rule__ProblemDiagram__Group__0 )
            {
             before(grammarAccess.getProblemDiagramAccess().getGroup()); 
            // InternalPf.g:69:3: ( rule__ProblemDiagram__Group__0 )
            // InternalPf.g:69:4: rule__ProblemDiagram__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ProblemDiagram__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getProblemDiagramAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleProblemDiagram"


    // $ANTLR start "entryRuleNode"
    // InternalPf.g:78:1: entryRuleNode : ruleNode EOF ;
    public final void entryRuleNode() throws RecognitionException {
        try {
            // InternalPf.g:79:1: ( ruleNode EOF )
            // InternalPf.g:80:1: ruleNode EOF
            {
             before(grammarAccess.getNodeRule()); 
            pushFollow(FOLLOW_1);
            ruleNode();

            state._fsp--;

             after(grammarAccess.getNodeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleNode"


    // $ANTLR start "ruleNode"
    // InternalPf.g:87:1: ruleNode : ( ( rule__Node__Group__0 ) ) ;
    public final void ruleNode() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:91:2: ( ( ( rule__Node__Group__0 ) ) )
            // InternalPf.g:92:2: ( ( rule__Node__Group__0 ) )
            {
            // InternalPf.g:92:2: ( ( rule__Node__Group__0 ) )
            // InternalPf.g:93:3: ( rule__Node__Group__0 )
            {
             before(grammarAccess.getNodeAccess().getGroup()); 
            // InternalPf.g:94:3: ( rule__Node__Group__0 )
            // InternalPf.g:94:4: rule__Node__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Node__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getNodeAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleNode"


    // $ANTLR start "entryRulePhenomenon"
    // InternalPf.g:103:1: entryRulePhenomenon : rulePhenomenon EOF ;
    public final void entryRulePhenomenon() throws RecognitionException {
        try {
            // InternalPf.g:104:1: ( rulePhenomenon EOF )
            // InternalPf.g:105:1: rulePhenomenon EOF
            {
             before(grammarAccess.getPhenomenonRule()); 
            pushFollow(FOLLOW_1);
            rulePhenomenon();

            state._fsp--;

             after(grammarAccess.getPhenomenonRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePhenomenon"


    // $ANTLR start "rulePhenomenon"
    // InternalPf.g:112:1: rulePhenomenon : ( ( rule__Phenomenon__Group__0 ) ) ;
    public final void rulePhenomenon() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:116:2: ( ( ( rule__Phenomenon__Group__0 ) ) )
            // InternalPf.g:117:2: ( ( rule__Phenomenon__Group__0 ) )
            {
            // InternalPf.g:117:2: ( ( rule__Phenomenon__Group__0 ) )
            // InternalPf.g:118:3: ( rule__Phenomenon__Group__0 )
            {
             before(grammarAccess.getPhenomenonAccess().getGroup()); 
            // InternalPf.g:119:3: ( rule__Phenomenon__Group__0 )
            // InternalPf.g:119:4: rule__Phenomenon__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Phenomenon__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPhenomenonAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePhenomenon"


    // $ANTLR start "entryRuleLink"
    // InternalPf.g:128:1: entryRuleLink : ruleLink EOF ;
    public final void entryRuleLink() throws RecognitionException {
        try {
            // InternalPf.g:129:1: ( ruleLink EOF )
            // InternalPf.g:130:1: ruleLink EOF
            {
             before(grammarAccess.getLinkRule()); 
            pushFollow(FOLLOW_1);
            ruleLink();

            state._fsp--;

             after(grammarAccess.getLinkRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleLink"


    // $ANTLR start "ruleLink"
    // InternalPf.g:137:1: ruleLink : ( ( rule__Link__Group__0 ) ) ;
    public final void ruleLink() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:141:2: ( ( ( rule__Link__Group__0 ) ) )
            // InternalPf.g:142:2: ( ( rule__Link__Group__0 ) )
            {
            // InternalPf.g:142:2: ( ( rule__Link__Group__0 ) )
            // InternalPf.g:143:3: ( rule__Link__Group__0 )
            {
             before(grammarAccess.getLinkAccess().getGroup()); 
            // InternalPf.g:144:3: ( rule__Link__Group__0 )
            // InternalPf.g:144:4: rule__Link__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Link__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getLinkAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLink"


    // $ANTLR start "ruleNodeType"
    // InternalPf.g:153:1: ruleNodeType : ( ( rule__NodeType__Alternatives ) ) ;
    public final void ruleNodeType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:157:1: ( ( ( rule__NodeType__Alternatives ) ) )
            // InternalPf.g:158:2: ( ( rule__NodeType__Alternatives ) )
            {
            // InternalPf.g:158:2: ( ( rule__NodeType__Alternatives ) )
            // InternalPf.g:159:3: ( rule__NodeType__Alternatives )
            {
             before(grammarAccess.getNodeTypeAccess().getAlternatives()); 
            // InternalPf.g:160:3: ( rule__NodeType__Alternatives )
            // InternalPf.g:160:4: rule__NodeType__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__NodeType__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getNodeTypeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleNodeType"


    // $ANTLR start "rulePhenomenonType"
    // InternalPf.g:169:1: rulePhenomenonType : ( ( rule__PhenomenonType__Alternatives ) ) ;
    public final void rulePhenomenonType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:173:1: ( ( ( rule__PhenomenonType__Alternatives ) ) )
            // InternalPf.g:174:2: ( ( rule__PhenomenonType__Alternatives ) )
            {
            // InternalPf.g:174:2: ( ( rule__PhenomenonType__Alternatives ) )
            // InternalPf.g:175:3: ( rule__PhenomenonType__Alternatives )
            {
             before(grammarAccess.getPhenomenonTypeAccess().getAlternatives()); 
            // InternalPf.g:176:3: ( rule__PhenomenonType__Alternatives )
            // InternalPf.g:176:4: rule__PhenomenonType__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__PhenomenonType__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getPhenomenonTypeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePhenomenonType"


    // $ANTLR start "ruleLinkType"
    // InternalPf.g:185:1: ruleLinkType : ( ( rule__LinkType__Alternatives ) ) ;
    public final void ruleLinkType() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:189:1: ( ( ( rule__LinkType__Alternatives ) ) )
            // InternalPf.g:190:2: ( ( rule__LinkType__Alternatives ) )
            {
            // InternalPf.g:190:2: ( ( rule__LinkType__Alternatives ) )
            // InternalPf.g:191:3: ( rule__LinkType__Alternatives )
            {
             before(grammarAccess.getLinkTypeAccess().getAlternatives()); 
            // InternalPf.g:192:3: ( rule__LinkType__Alternatives )
            // InternalPf.g:192:4: rule__LinkType__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__LinkType__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getLinkTypeAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleLinkType"


    // $ANTLR start "rule__ProblemDiagram__Alternatives_3"
    // InternalPf.g:200:1: rule__ProblemDiagram__Alternatives_3 : ( ( ( rule__ProblemDiagram__NodesAssignment_3_0 ) ) | ( ( rule__ProblemDiagram__LinksAssignment_3_1 ) ) );
    public final void rule__ProblemDiagram__Alternatives_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:204:1: ( ( ( rule__ProblemDiagram__NodesAssignment_3_0 ) ) | ( ( rule__ProblemDiagram__LinksAssignment_3_1 ) ) )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==RULE_ID) ) {
                int LA1_1 = input.LA(2);

                if ( ((LA1_1>=12 && LA1_1<=19)) ) {
                    alt1=1;
                }
                else if ( ((LA1_1>=24 && LA1_1<=28)) ) {
                    alt1=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalPf.g:205:2: ( ( rule__ProblemDiagram__NodesAssignment_3_0 ) )
                    {
                    // InternalPf.g:205:2: ( ( rule__ProblemDiagram__NodesAssignment_3_0 ) )
                    // InternalPf.g:206:3: ( rule__ProblemDiagram__NodesAssignment_3_0 )
                    {
                     before(grammarAccess.getProblemDiagramAccess().getNodesAssignment_3_0()); 
                    // InternalPf.g:207:3: ( rule__ProblemDiagram__NodesAssignment_3_0 )
                    // InternalPf.g:207:4: rule__ProblemDiagram__NodesAssignment_3_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProblemDiagram__NodesAssignment_3_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getProblemDiagramAccess().getNodesAssignment_3_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalPf.g:211:2: ( ( rule__ProblemDiagram__LinksAssignment_3_1 ) )
                    {
                    // InternalPf.g:211:2: ( ( rule__ProblemDiagram__LinksAssignment_3_1 ) )
                    // InternalPf.g:212:3: ( rule__ProblemDiagram__LinksAssignment_3_1 )
                    {
                     before(grammarAccess.getProblemDiagramAccess().getLinksAssignment_3_1()); 
                    // InternalPf.g:213:3: ( rule__ProblemDiagram__LinksAssignment_3_1 )
                    // InternalPf.g:213:4: rule__ProblemDiagram__LinksAssignment_3_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProblemDiagram__LinksAssignment_3_1();

                    state._fsp--;


                    }

                     after(grammarAccess.getProblemDiagramAccess().getLinksAssignment_3_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__Alternatives_3"


    // $ANTLR start "rule__Node__Alternatives_3_2"
    // InternalPf.g:221:1: rule__Node__Alternatives_3_2 : ( ( ( rule__Node__SubproblemAssignment_3_2_0 ) ) | ( ( rule__Node__Group_3_2_1__0 ) ) | ( ( rule__Node__Group_3_2_2__0 ) ) | ( ( rule__Node__Group_3_2_3__0 ) ) );
    public final void rule__Node__Alternatives_3_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:225:1: ( ( ( rule__Node__SubproblemAssignment_3_2_0 ) ) | ( ( rule__Node__Group_3_2_1__0 ) ) | ( ( rule__Node__Group_3_2_2__0 ) ) | ( ( rule__Node__Group_3_2_3__0 ) ) )
            int alt2=4;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==29) ) {
                alt2=1;
            }
            else if ( (LA2_0==35) ) {
                switch ( input.LA(2) ) {
                case 36:
                    {
                    alt2=2;
                    }
                    break;
                case 37:
                    {
                    alt2=3;
                    }
                    break;
                case RULE_STRING:
                    {
                    alt2=4;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 2, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalPf.g:226:2: ( ( rule__Node__SubproblemAssignment_3_2_0 ) )
                    {
                    // InternalPf.g:226:2: ( ( rule__Node__SubproblemAssignment_3_2_0 ) )
                    // InternalPf.g:227:3: ( rule__Node__SubproblemAssignment_3_2_0 )
                    {
                     before(grammarAccess.getNodeAccess().getSubproblemAssignment_3_2_0()); 
                    // InternalPf.g:228:3: ( rule__Node__SubproblemAssignment_3_2_0 )
                    // InternalPf.g:228:4: rule__Node__SubproblemAssignment_3_2_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Node__SubproblemAssignment_3_2_0();

                    state._fsp--;


                    }

                     after(grammarAccess.getNodeAccess().getSubproblemAssignment_3_2_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalPf.g:232:2: ( ( rule__Node__Group_3_2_1__0 ) )
                    {
                    // InternalPf.g:232:2: ( ( rule__Node__Group_3_2_1__0 ) )
                    // InternalPf.g:233:3: ( rule__Node__Group_3_2_1__0 )
                    {
                     before(grammarAccess.getNodeAccess().getGroup_3_2_1()); 
                    // InternalPf.g:234:3: ( rule__Node__Group_3_2_1__0 )
                    // InternalPf.g:234:4: rule__Node__Group_3_2_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Node__Group_3_2_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getNodeAccess().getGroup_3_2_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalPf.g:238:2: ( ( rule__Node__Group_3_2_2__0 ) )
                    {
                    // InternalPf.g:238:2: ( ( rule__Node__Group_3_2_2__0 ) )
                    // InternalPf.g:239:3: ( rule__Node__Group_3_2_2__0 )
                    {
                     before(grammarAccess.getNodeAccess().getGroup_3_2_2()); 
                    // InternalPf.g:240:3: ( rule__Node__Group_3_2_2__0 )
                    // InternalPf.g:240:4: rule__Node__Group_3_2_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Node__Group_3_2_2__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getNodeAccess().getGroup_3_2_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalPf.g:244:2: ( ( rule__Node__Group_3_2_3__0 ) )
                    {
                    // InternalPf.g:244:2: ( ( rule__Node__Group_3_2_3__0 ) )
                    // InternalPf.g:245:3: ( rule__Node__Group_3_2_3__0 )
                    {
                     before(grammarAccess.getNodeAccess().getGroup_3_2_3()); 
                    // InternalPf.g:246:3: ( rule__Node__Group_3_2_3__0 )
                    // InternalPf.g:246:4: rule__Node__Group_3_2_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Node__Group_3_2_3__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getNodeAccess().getGroup_3_2_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Alternatives_3_2"


    // $ANTLR start "rule__NodeType__Alternatives"
    // InternalPf.g:254:1: rule__NodeType__Alternatives : ( ( ( 'R' ) ) | ( ( 'M' ) ) | ( ( 'B' ) ) | ( ( 'X' ) ) | ( ( 'C' ) ) | ( ( 'D' ) ) | ( ( 'P' ) ) | ( ( '?' ) ) );
    public final void rule__NodeType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:258:1: ( ( ( 'R' ) ) | ( ( 'M' ) ) | ( ( 'B' ) ) | ( ( 'X' ) ) | ( ( 'C' ) ) | ( ( 'D' ) ) | ( ( 'P' ) ) | ( ( '?' ) ) )
            int alt3=8;
            switch ( input.LA(1) ) {
            case 12:
                {
                alt3=1;
                }
                break;
            case 13:
                {
                alt3=2;
                }
                break;
            case 14:
                {
                alt3=3;
                }
                break;
            case 15:
                {
                alt3=4;
                }
                break;
            case 16:
                {
                alt3=5;
                }
                break;
            case 17:
                {
                alt3=6;
                }
                break;
            case 18:
                {
                alt3=7;
                }
                break;
            case 19:
                {
                alt3=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // InternalPf.g:259:2: ( ( 'R' ) )
                    {
                    // InternalPf.g:259:2: ( ( 'R' ) )
                    // InternalPf.g:260:3: ( 'R' )
                    {
                     before(grammarAccess.getNodeTypeAccess().getREQUIREMENTEnumLiteralDeclaration_0()); 
                    // InternalPf.g:261:3: ( 'R' )
                    // InternalPf.g:261:4: 'R'
                    {
                    match(input,12,FOLLOW_2); 

                    }

                     after(grammarAccess.getNodeTypeAccess().getREQUIREMENTEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalPf.g:265:2: ( ( 'M' ) )
                    {
                    // InternalPf.g:265:2: ( ( 'M' ) )
                    // InternalPf.g:266:3: ( 'M' )
                    {
                     before(grammarAccess.getNodeTypeAccess().getMACHINEEnumLiteralDeclaration_1()); 
                    // InternalPf.g:267:3: ( 'M' )
                    // InternalPf.g:267:4: 'M'
                    {
                    match(input,13,FOLLOW_2); 

                    }

                     after(grammarAccess.getNodeTypeAccess().getMACHINEEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalPf.g:271:2: ( ( 'B' ) )
                    {
                    // InternalPf.g:271:2: ( ( 'B' ) )
                    // InternalPf.g:272:3: ( 'B' )
                    {
                     before(grammarAccess.getNodeTypeAccess().getBIDDABLEEnumLiteralDeclaration_2()); 
                    // InternalPf.g:273:3: ( 'B' )
                    // InternalPf.g:273:4: 'B'
                    {
                    match(input,14,FOLLOW_2); 

                    }

                     after(grammarAccess.getNodeTypeAccess().getBIDDABLEEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalPf.g:277:2: ( ( 'X' ) )
                    {
                    // InternalPf.g:277:2: ( ( 'X' ) )
                    // InternalPf.g:278:3: ( 'X' )
                    {
                     before(grammarAccess.getNodeTypeAccess().getLEXICALEnumLiteralDeclaration_3()); 
                    // InternalPf.g:279:3: ( 'X' )
                    // InternalPf.g:279:4: 'X'
                    {
                    match(input,15,FOLLOW_2); 

                    }

                     after(grammarAccess.getNodeTypeAccess().getLEXICALEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalPf.g:283:2: ( ( 'C' ) )
                    {
                    // InternalPf.g:283:2: ( ( 'C' ) )
                    // InternalPf.g:284:3: ( 'C' )
                    {
                     before(grammarAccess.getNodeTypeAccess().getCAUSALEnumLiteralDeclaration_4()); 
                    // InternalPf.g:285:3: ( 'C' )
                    // InternalPf.g:285:4: 'C'
                    {
                    match(input,16,FOLLOW_2); 

                    }

                     after(grammarAccess.getNodeTypeAccess().getCAUSALEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalPf.g:289:2: ( ( 'D' ) )
                    {
                    // InternalPf.g:289:2: ( ( 'D' ) )
                    // InternalPf.g:290:3: ( 'D' )
                    {
                     before(grammarAccess.getNodeTypeAccess().getDESIGNEDEnumLiteralDeclaration_5()); 
                    // InternalPf.g:291:3: ( 'D' )
                    // InternalPf.g:291:4: 'D'
                    {
                    match(input,17,FOLLOW_2); 

                    }

                     after(grammarAccess.getNodeTypeAccess().getDESIGNEDEnumLiteralDeclaration_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalPf.g:295:2: ( ( 'P' ) )
                    {
                    // InternalPf.g:295:2: ( ( 'P' ) )
                    // InternalPf.g:296:3: ( 'P' )
                    {
                     before(grammarAccess.getNodeTypeAccess().getPHYSICALEnumLiteralDeclaration_6()); 
                    // InternalPf.g:297:3: ( 'P' )
                    // InternalPf.g:297:4: 'P'
                    {
                    match(input,18,FOLLOW_2); 

                    }

                     after(grammarAccess.getNodeTypeAccess().getPHYSICALEnumLiteralDeclaration_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalPf.g:301:2: ( ( '?' ) )
                    {
                    // InternalPf.g:301:2: ( ( '?' ) )
                    // InternalPf.g:302:3: ( '?' )
                    {
                     before(grammarAccess.getNodeTypeAccess().getCONCERNEnumLiteralDeclaration_7()); 
                    // InternalPf.g:303:3: ( '?' )
                    // InternalPf.g:303:4: '?'
                    {
                    match(input,19,FOLLOW_2); 

                    }

                     after(grammarAccess.getNodeTypeAccess().getCONCERNEnumLiteralDeclaration_7()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__NodeType__Alternatives"


    // $ANTLR start "rule__PhenomenonType__Alternatives"
    // InternalPf.g:311:1: rule__PhenomenonType__Alternatives : ( ( ( 'phenomenon' ) ) | ( ( 'event' ) ) | ( ( 'state' ) ) | ( ( 'value' ) ) );
    public final void rule__PhenomenonType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:315:1: ( ( ( 'phenomenon' ) ) | ( ( 'event' ) ) | ( ( 'state' ) ) | ( ( 'value' ) ) )
            int alt4=4;
            switch ( input.LA(1) ) {
            case 20:
                {
                alt4=1;
                }
                break;
            case 21:
                {
                alt4=2;
                }
                break;
            case 22:
                {
                alt4=3;
                }
                break;
            case 23:
                {
                alt4=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalPf.g:316:2: ( ( 'phenomenon' ) )
                    {
                    // InternalPf.g:316:2: ( ( 'phenomenon' ) )
                    // InternalPf.g:317:3: ( 'phenomenon' )
                    {
                     before(grammarAccess.getPhenomenonTypeAccess().getUNSPECIFIEDEnumLiteralDeclaration_0()); 
                    // InternalPf.g:318:3: ( 'phenomenon' )
                    // InternalPf.g:318:4: 'phenomenon'
                    {
                    match(input,20,FOLLOW_2); 

                    }

                     after(grammarAccess.getPhenomenonTypeAccess().getUNSPECIFIEDEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalPf.g:322:2: ( ( 'event' ) )
                    {
                    // InternalPf.g:322:2: ( ( 'event' ) )
                    // InternalPf.g:323:3: ( 'event' )
                    {
                     before(grammarAccess.getPhenomenonTypeAccess().getEVENTEnumLiteralDeclaration_1()); 
                    // InternalPf.g:324:3: ( 'event' )
                    // InternalPf.g:324:4: 'event'
                    {
                    match(input,21,FOLLOW_2); 

                    }

                     after(grammarAccess.getPhenomenonTypeAccess().getEVENTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalPf.g:328:2: ( ( 'state' ) )
                    {
                    // InternalPf.g:328:2: ( ( 'state' ) )
                    // InternalPf.g:329:3: ( 'state' )
                    {
                     before(grammarAccess.getPhenomenonTypeAccess().getSTATEEnumLiteralDeclaration_2()); 
                    // InternalPf.g:330:3: ( 'state' )
                    // InternalPf.g:330:4: 'state'
                    {
                    match(input,22,FOLLOW_2); 

                    }

                     after(grammarAccess.getPhenomenonTypeAccess().getSTATEEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalPf.g:334:2: ( ( 'value' ) )
                    {
                    // InternalPf.g:334:2: ( ( 'value' ) )
                    // InternalPf.g:335:3: ( 'value' )
                    {
                     before(grammarAccess.getPhenomenonTypeAccess().getVALUEEnumLiteralDeclaration_3()); 
                    // InternalPf.g:336:3: ( 'value' )
                    // InternalPf.g:336:4: 'value'
                    {
                    match(input,23,FOLLOW_2); 

                    }

                     after(grammarAccess.getPhenomenonTypeAccess().getVALUEEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__PhenomenonType__Alternatives"


    // $ANTLR start "rule__LinkType__Alternatives"
    // InternalPf.g:344:1: rule__LinkType__Alternatives : ( ( ( '--' ) ) | ( ( '~~' ) ) | ( ( '<~' ) ) | ( ( '->' ) ) | ( ( '~>' ) ) );
    public final void rule__LinkType__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:348:1: ( ( ( '--' ) ) | ( ( '~~' ) ) | ( ( '<~' ) ) | ( ( '->' ) ) | ( ( '~>' ) ) )
            int alt5=5;
            switch ( input.LA(1) ) {
            case 24:
                {
                alt5=1;
                }
                break;
            case 25:
                {
                alt5=2;
                }
                break;
            case 26:
                {
                alt5=3;
                }
                break;
            case 27:
                {
                alt5=4;
                }
                break;
            case 28:
                {
                alt5=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // InternalPf.g:349:2: ( ( '--' ) )
                    {
                    // InternalPf.g:349:2: ( ( '--' ) )
                    // InternalPf.g:350:3: ( '--' )
                    {
                     before(grammarAccess.getLinkTypeAccess().getINTERFACEEnumLiteralDeclaration_0()); 
                    // InternalPf.g:351:3: ( '--' )
                    // InternalPf.g:351:4: '--'
                    {
                    match(input,24,FOLLOW_2); 

                    }

                     after(grammarAccess.getLinkTypeAccess().getINTERFACEEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalPf.g:355:2: ( ( '~~' ) )
                    {
                    // InternalPf.g:355:2: ( ( '~~' ) )
                    // InternalPf.g:356:3: ( '~~' )
                    {
                     before(grammarAccess.getLinkTypeAccess().getREFERENCEEnumLiteralDeclaration_1()); 
                    // InternalPf.g:357:3: ( '~~' )
                    // InternalPf.g:357:4: '~~'
                    {
                    match(input,25,FOLLOW_2); 

                    }

                     after(grammarAccess.getLinkTypeAccess().getREFERENCEEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalPf.g:361:2: ( ( '<~' ) )
                    {
                    // InternalPf.g:361:2: ( ( '<~' ) )
                    // InternalPf.g:362:3: ( '<~' )
                    {
                     before(grammarAccess.getLinkTypeAccess().getCONSTRAINTEnumLiteralDeclaration_2()); 
                    // InternalPf.g:363:3: ( '<~' )
                    // InternalPf.g:363:4: '<~'
                    {
                    match(input,26,FOLLOW_2); 

                    }

                     after(grammarAccess.getLinkTypeAccess().getCONSTRAINTEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalPf.g:367:2: ( ( '->' ) )
                    {
                    // InternalPf.g:367:2: ( ( '->' ) )
                    // InternalPf.g:368:3: ( '->' )
                    {
                     before(grammarAccess.getLinkTypeAccess().getCONCERNEnumLiteralDeclaration_3()); 
                    // InternalPf.g:369:3: ( '->' )
                    // InternalPf.g:369:4: '->'
                    {
                    match(input,27,FOLLOW_2); 

                    }

                     after(grammarAccess.getLinkTypeAccess().getCONCERNEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalPf.g:373:2: ( ( '~>' ) )
                    {
                    // InternalPf.g:373:2: ( ( '~>' ) )
                    // InternalPf.g:374:3: ( '~>' )
                    {
                     before(grammarAccess.getLinkTypeAccess().getINV_CONSTRAINTEnumLiteralDeclaration_4()); 
                    // InternalPf.g:375:3: ( '~>' )
                    // InternalPf.g:375:4: '~>'
                    {
                    match(input,28,FOLLOW_2); 

                    }

                     after(grammarAccess.getLinkTypeAccess().getINV_CONSTRAINTEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__LinkType__Alternatives"


    // $ANTLR start "rule__ProblemDiagram__Group__0"
    // InternalPf.g:383:1: rule__ProblemDiagram__Group__0 : rule__ProblemDiagram__Group__0__Impl rule__ProblemDiagram__Group__1 ;
    public final void rule__ProblemDiagram__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:387:1: ( rule__ProblemDiagram__Group__0__Impl rule__ProblemDiagram__Group__1 )
            // InternalPf.g:388:2: rule__ProblemDiagram__Group__0__Impl rule__ProblemDiagram__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__ProblemDiagram__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProblemDiagram__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__Group__0"


    // $ANTLR start "rule__ProblemDiagram__Group__0__Impl"
    // InternalPf.g:395:1: rule__ProblemDiagram__Group__0__Impl : ( 'problem:' ) ;
    public final void rule__ProblemDiagram__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:399:1: ( ( 'problem:' ) )
            // InternalPf.g:400:1: ( 'problem:' )
            {
            // InternalPf.g:400:1: ( 'problem:' )
            // InternalPf.g:401:2: 'problem:'
            {
             before(grammarAccess.getProblemDiagramAccess().getProblemKeyword_0()); 
            match(input,29,FOLLOW_2); 
             after(grammarAccess.getProblemDiagramAccess().getProblemKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__Group__0__Impl"


    // $ANTLR start "rule__ProblemDiagram__Group__1"
    // InternalPf.g:410:1: rule__ProblemDiagram__Group__1 : rule__ProblemDiagram__Group__1__Impl rule__ProblemDiagram__Group__2 ;
    public final void rule__ProblemDiagram__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:414:1: ( rule__ProblemDiagram__Group__1__Impl rule__ProblemDiagram__Group__2 )
            // InternalPf.g:415:2: rule__ProblemDiagram__Group__1__Impl rule__ProblemDiagram__Group__2
            {
            pushFollow(FOLLOW_4);
            rule__ProblemDiagram__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProblemDiagram__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__Group__1"


    // $ANTLR start "rule__ProblemDiagram__Group__1__Impl"
    // InternalPf.g:422:1: rule__ProblemDiagram__Group__1__Impl : ( ( rule__ProblemDiagram__NameAssignment_1 ) ) ;
    public final void rule__ProblemDiagram__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:426:1: ( ( ( rule__ProblemDiagram__NameAssignment_1 ) ) )
            // InternalPf.g:427:1: ( ( rule__ProblemDiagram__NameAssignment_1 ) )
            {
            // InternalPf.g:427:1: ( ( rule__ProblemDiagram__NameAssignment_1 ) )
            // InternalPf.g:428:2: ( rule__ProblemDiagram__NameAssignment_1 )
            {
             before(grammarAccess.getProblemDiagramAccess().getNameAssignment_1()); 
            // InternalPf.g:429:2: ( rule__ProblemDiagram__NameAssignment_1 )
            // InternalPf.g:429:3: rule__ProblemDiagram__NameAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__ProblemDiagram__NameAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getProblemDiagramAccess().getNameAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__Group__1__Impl"


    // $ANTLR start "rule__ProblemDiagram__Group__2"
    // InternalPf.g:437:1: rule__ProblemDiagram__Group__2 : rule__ProblemDiagram__Group__2__Impl rule__ProblemDiagram__Group__3 ;
    public final void rule__ProblemDiagram__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:441:1: ( rule__ProblemDiagram__Group__2__Impl rule__ProblemDiagram__Group__3 )
            // InternalPf.g:442:2: rule__ProblemDiagram__Group__2__Impl rule__ProblemDiagram__Group__3
            {
            pushFollow(FOLLOW_4);
            rule__ProblemDiagram__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProblemDiagram__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__Group__2"


    // $ANTLR start "rule__ProblemDiagram__Group__2__Impl"
    // InternalPf.g:449:1: rule__ProblemDiagram__Group__2__Impl : ( ( rule__ProblemDiagram__Group_2__0 )? ) ;
    public final void rule__ProblemDiagram__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:453:1: ( ( ( rule__ProblemDiagram__Group_2__0 )? ) )
            // InternalPf.g:454:1: ( ( rule__ProblemDiagram__Group_2__0 )? )
            {
            // InternalPf.g:454:1: ( ( rule__ProblemDiagram__Group_2__0 )? )
            // InternalPf.g:455:2: ( rule__ProblemDiagram__Group_2__0 )?
            {
             before(grammarAccess.getProblemDiagramAccess().getGroup_2()); 
            // InternalPf.g:456:2: ( rule__ProblemDiagram__Group_2__0 )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==30) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalPf.g:456:3: rule__ProblemDiagram__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__ProblemDiagram__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getProblemDiagramAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__Group__2__Impl"


    // $ANTLR start "rule__ProblemDiagram__Group__3"
    // InternalPf.g:464:1: rule__ProblemDiagram__Group__3 : rule__ProblemDiagram__Group__3__Impl ;
    public final void rule__ProblemDiagram__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:468:1: ( rule__ProblemDiagram__Group__3__Impl )
            // InternalPf.g:469:2: rule__ProblemDiagram__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProblemDiagram__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__Group__3"


    // $ANTLR start "rule__ProblemDiagram__Group__3__Impl"
    // InternalPf.g:475:1: rule__ProblemDiagram__Group__3__Impl : ( ( rule__ProblemDiagram__Alternatives_3 )* ) ;
    public final void rule__ProblemDiagram__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:479:1: ( ( ( rule__ProblemDiagram__Alternatives_3 )* ) )
            // InternalPf.g:480:1: ( ( rule__ProblemDiagram__Alternatives_3 )* )
            {
            // InternalPf.g:480:1: ( ( rule__ProblemDiagram__Alternatives_3 )* )
            // InternalPf.g:481:2: ( rule__ProblemDiagram__Alternatives_3 )*
            {
             before(grammarAccess.getProblemDiagramAccess().getAlternatives_3()); 
            // InternalPf.g:482:2: ( rule__ProblemDiagram__Alternatives_3 )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==RULE_ID) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalPf.g:482:3: rule__ProblemDiagram__Alternatives_3
            	    {
            	    pushFollow(FOLLOW_5);
            	    rule__ProblemDiagram__Alternatives_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

             after(grammarAccess.getProblemDiagramAccess().getAlternatives_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__Group__3__Impl"


    // $ANTLR start "rule__ProblemDiagram__Group_2__0"
    // InternalPf.g:491:1: rule__ProblemDiagram__Group_2__0 : rule__ProblemDiagram__Group_2__0__Impl rule__ProblemDiagram__Group_2__1 ;
    public final void rule__ProblemDiagram__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:495:1: ( rule__ProblemDiagram__Group_2__0__Impl rule__ProblemDiagram__Group_2__1 )
            // InternalPf.g:496:2: rule__ProblemDiagram__Group_2__0__Impl rule__ProblemDiagram__Group_2__1
            {
            pushFollow(FOLLOW_3);
            rule__ProblemDiagram__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ProblemDiagram__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__Group_2__0"


    // $ANTLR start "rule__ProblemDiagram__Group_2__0__Impl"
    // InternalPf.g:503:1: rule__ProblemDiagram__Group_2__0__Impl : ( 'for' ) ;
    public final void rule__ProblemDiagram__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:507:1: ( ( 'for' ) )
            // InternalPf.g:508:1: ( 'for' )
            {
            // InternalPf.g:508:1: ( 'for' )
            // InternalPf.g:509:2: 'for'
            {
             before(grammarAccess.getProblemDiagramAccess().getForKeyword_2_0()); 
            match(input,30,FOLLOW_2); 
             after(grammarAccess.getProblemDiagramAccess().getForKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__Group_2__0__Impl"


    // $ANTLR start "rule__ProblemDiagram__Group_2__1"
    // InternalPf.g:518:1: rule__ProblemDiagram__Group_2__1 : rule__ProblemDiagram__Group_2__1__Impl ;
    public final void rule__ProblemDiagram__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:522:1: ( rule__ProblemDiagram__Group_2__1__Impl )
            // InternalPf.g:523:2: rule__ProblemDiagram__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ProblemDiagram__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__Group_2__1"


    // $ANTLR start "rule__ProblemDiagram__Group_2__1__Impl"
    // InternalPf.g:529:1: rule__ProblemDiagram__Group_2__1__Impl : ( ( rule__ProblemDiagram__HighlightAssignment_2_1 ) ) ;
    public final void rule__ProblemDiagram__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:533:1: ( ( ( rule__ProblemDiagram__HighlightAssignment_2_1 ) ) )
            // InternalPf.g:534:1: ( ( rule__ProblemDiagram__HighlightAssignment_2_1 ) )
            {
            // InternalPf.g:534:1: ( ( rule__ProblemDiagram__HighlightAssignment_2_1 ) )
            // InternalPf.g:535:2: ( rule__ProblemDiagram__HighlightAssignment_2_1 )
            {
             before(grammarAccess.getProblemDiagramAccess().getHighlightAssignment_2_1()); 
            // InternalPf.g:536:2: ( rule__ProblemDiagram__HighlightAssignment_2_1 )
            // InternalPf.g:536:3: rule__ProblemDiagram__HighlightAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__ProblemDiagram__HighlightAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getProblemDiagramAccess().getHighlightAssignment_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__Group_2__1__Impl"


    // $ANTLR start "rule__Node__Group__0"
    // InternalPf.g:545:1: rule__Node__Group__0 : rule__Node__Group__0__Impl rule__Node__Group__1 ;
    public final void rule__Node__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:549:1: ( rule__Node__Group__0__Impl rule__Node__Group__1 )
            // InternalPf.g:550:2: rule__Node__Group__0__Impl rule__Node__Group__1
            {
            pushFollow(FOLLOW_6);
            rule__Node__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group__0"


    // $ANTLR start "rule__Node__Group__0__Impl"
    // InternalPf.g:557:1: rule__Node__Group__0__Impl : ( ( rule__Node__NameAssignment_0 ) ) ;
    public final void rule__Node__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:561:1: ( ( ( rule__Node__NameAssignment_0 ) ) )
            // InternalPf.g:562:1: ( ( rule__Node__NameAssignment_0 ) )
            {
            // InternalPf.g:562:1: ( ( rule__Node__NameAssignment_0 ) )
            // InternalPf.g:563:2: ( rule__Node__NameAssignment_0 )
            {
             before(grammarAccess.getNodeAccess().getNameAssignment_0()); 
            // InternalPf.g:564:2: ( rule__Node__NameAssignment_0 )
            // InternalPf.g:564:3: rule__Node__NameAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__Node__NameAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getNodeAccess().getNameAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group__0__Impl"


    // $ANTLR start "rule__Node__Group__1"
    // InternalPf.g:572:1: rule__Node__Group__1 : rule__Node__Group__1__Impl rule__Node__Group__2 ;
    public final void rule__Node__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:576:1: ( rule__Node__Group__1__Impl rule__Node__Group__2 )
            // InternalPf.g:577:2: rule__Node__Group__1__Impl rule__Node__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__Node__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group__1"


    // $ANTLR start "rule__Node__Group__1__Impl"
    // InternalPf.g:584:1: rule__Node__Group__1__Impl : ( ( rule__Node__TypeAssignment_1 ) ) ;
    public final void rule__Node__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:588:1: ( ( ( rule__Node__TypeAssignment_1 ) ) )
            // InternalPf.g:589:1: ( ( rule__Node__TypeAssignment_1 ) )
            {
            // InternalPf.g:589:1: ( ( rule__Node__TypeAssignment_1 ) )
            // InternalPf.g:590:2: ( rule__Node__TypeAssignment_1 )
            {
             before(grammarAccess.getNodeAccess().getTypeAssignment_1()); 
            // InternalPf.g:591:2: ( rule__Node__TypeAssignment_1 )
            // InternalPf.g:591:3: rule__Node__TypeAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Node__TypeAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getNodeAccess().getTypeAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group__1__Impl"


    // $ANTLR start "rule__Node__Group__2"
    // InternalPf.g:599:1: rule__Node__Group__2 : rule__Node__Group__2__Impl rule__Node__Group__3 ;
    public final void rule__Node__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:603:1: ( rule__Node__Group__2__Impl rule__Node__Group__3 )
            // InternalPf.g:604:2: rule__Node__Group__2__Impl rule__Node__Group__3
            {
            pushFollow(FOLLOW_7);
            rule__Node__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group__2"


    // $ANTLR start "rule__Node__Group__2__Impl"
    // InternalPf.g:611:1: rule__Node__Group__2__Impl : ( ( rule__Node__Group_2__0 )? ) ;
    public final void rule__Node__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:615:1: ( ( ( rule__Node__Group_2__0 )? ) )
            // InternalPf.g:616:1: ( ( rule__Node__Group_2__0 )? )
            {
            // InternalPf.g:616:1: ( ( rule__Node__Group_2__0 )? )
            // InternalPf.g:617:2: ( rule__Node__Group_2__0 )?
            {
             before(grammarAccess.getNodeAccess().getGroup_2()); 
            // InternalPf.g:618:2: ( rule__Node__Group_2__0 )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==RULE_STRING||LA8_0==31) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalPf.g:618:3: rule__Node__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Node__Group_2__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getNodeAccess().getGroup_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group__2__Impl"


    // $ANTLR start "rule__Node__Group__3"
    // InternalPf.g:626:1: rule__Node__Group__3 : rule__Node__Group__3__Impl ;
    public final void rule__Node__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:630:1: ( rule__Node__Group__3__Impl )
            // InternalPf.g:631:2: rule__Node__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Node__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group__3"


    // $ANTLR start "rule__Node__Group__3__Impl"
    // InternalPf.g:637:1: rule__Node__Group__3__Impl : ( ( rule__Node__Group_3__0 )? ) ;
    public final void rule__Node__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:641:1: ( ( ( rule__Node__Group_3__0 )? ) )
            // InternalPf.g:642:1: ( ( rule__Node__Group_3__0 )? )
            {
            // InternalPf.g:642:1: ( ( rule__Node__Group_3__0 )? )
            // InternalPf.g:643:2: ( rule__Node__Group_3__0 )?
            {
             before(grammarAccess.getNodeAccess().getGroup_3()); 
            // InternalPf.g:644:2: ( rule__Node__Group_3__0 )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==32) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalPf.g:644:3: rule__Node__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Node__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getNodeAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group__3__Impl"


    // $ANTLR start "rule__Node__Group_2__0"
    // InternalPf.g:653:1: rule__Node__Group_2__0 : rule__Node__Group_2__0__Impl rule__Node__Group_2__1 ;
    public final void rule__Node__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:657:1: ( rule__Node__Group_2__0__Impl rule__Node__Group_2__1 )
            // InternalPf.g:658:2: rule__Node__Group_2__0__Impl rule__Node__Group_2__1
            {
            pushFollow(FOLLOW_8);
            rule__Node__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_2__0"


    // $ANTLR start "rule__Node__Group_2__0__Impl"
    // InternalPf.g:665:1: rule__Node__Group_2__0__Impl : ( ( ':' )? ) ;
    public final void rule__Node__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:669:1: ( ( ( ':' )? ) )
            // InternalPf.g:670:1: ( ( ':' )? )
            {
            // InternalPf.g:670:1: ( ( ':' )? )
            // InternalPf.g:671:2: ( ':' )?
            {
             before(grammarAccess.getNodeAccess().getColonKeyword_2_0()); 
            // InternalPf.g:672:2: ( ':' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==31) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalPf.g:672:3: ':'
                    {
                    match(input,31,FOLLOW_2); 

                    }
                    break;

            }

             after(grammarAccess.getNodeAccess().getColonKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_2__0__Impl"


    // $ANTLR start "rule__Node__Group_2__1"
    // InternalPf.g:680:1: rule__Node__Group_2__1 : rule__Node__Group_2__1__Impl ;
    public final void rule__Node__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:684:1: ( rule__Node__Group_2__1__Impl )
            // InternalPf.g:685:2: rule__Node__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Node__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_2__1"


    // $ANTLR start "rule__Node__Group_2__1__Impl"
    // InternalPf.g:691:1: rule__Node__Group_2__1__Impl : ( ( rule__Node__DescriptionAssignment_2_1 ) ) ;
    public final void rule__Node__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:695:1: ( ( ( rule__Node__DescriptionAssignment_2_1 ) ) )
            // InternalPf.g:696:1: ( ( rule__Node__DescriptionAssignment_2_1 ) )
            {
            // InternalPf.g:696:1: ( ( rule__Node__DescriptionAssignment_2_1 ) )
            // InternalPf.g:697:2: ( rule__Node__DescriptionAssignment_2_1 )
            {
             before(grammarAccess.getNodeAccess().getDescriptionAssignment_2_1()); 
            // InternalPf.g:698:2: ( rule__Node__DescriptionAssignment_2_1 )
            // InternalPf.g:698:3: rule__Node__DescriptionAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__Node__DescriptionAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getNodeAccess().getDescriptionAssignment_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_2__1__Impl"


    // $ANTLR start "rule__Node__Group_3__0"
    // InternalPf.g:707:1: rule__Node__Group_3__0 : rule__Node__Group_3__0__Impl rule__Node__Group_3__1 ;
    public final void rule__Node__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:711:1: ( rule__Node__Group_3__0__Impl rule__Node__Group_3__1 )
            // InternalPf.g:712:2: rule__Node__Group_3__0__Impl rule__Node__Group_3__1
            {
            pushFollow(FOLLOW_9);
            rule__Node__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3__0"


    // $ANTLR start "rule__Node__Group_3__0__Impl"
    // InternalPf.g:719:1: rule__Node__Group_3__0__Impl : ( '{' ) ;
    public final void rule__Node__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:723:1: ( ( '{' ) )
            // InternalPf.g:724:1: ( '{' )
            {
            // InternalPf.g:724:1: ( '{' )
            // InternalPf.g:725:2: '{'
            {
             before(grammarAccess.getNodeAccess().getLeftCurlyBracketKeyword_3_0()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getNodeAccess().getLeftCurlyBracketKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3__0__Impl"


    // $ANTLR start "rule__Node__Group_3__1"
    // InternalPf.g:734:1: rule__Node__Group_3__1 : rule__Node__Group_3__1__Impl rule__Node__Group_3__2 ;
    public final void rule__Node__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:738:1: ( rule__Node__Group_3__1__Impl rule__Node__Group_3__2 )
            // InternalPf.g:739:2: rule__Node__Group_3__1__Impl rule__Node__Group_3__2
            {
            pushFollow(FOLLOW_9);
            rule__Node__Group_3__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group_3__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3__1"


    // $ANTLR start "rule__Node__Group_3__1__Impl"
    // InternalPf.g:746:1: rule__Node__Group_3__1__Impl : ( ( rule__Node__Group_3_1__0 )? ) ;
    public final void rule__Node__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:750:1: ( ( ( rule__Node__Group_3_1__0 )? ) )
            // InternalPf.g:751:1: ( ( rule__Node__Group_3_1__0 )? )
            {
            // InternalPf.g:751:1: ( ( rule__Node__Group_3_1__0 )? )
            // InternalPf.g:752:2: ( rule__Node__Group_3_1__0 )?
            {
             before(grammarAccess.getNodeAccess().getGroup_3_1()); 
            // InternalPf.g:753:2: ( rule__Node__Group_3_1__0 )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==RULE_ID||(LA11_0>=20 && LA11_0<=23)||LA11_0==38) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalPf.g:753:3: rule__Node__Group_3_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Node__Group_3_1__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getNodeAccess().getGroup_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3__1__Impl"


    // $ANTLR start "rule__Node__Group_3__2"
    // InternalPf.g:761:1: rule__Node__Group_3__2 : rule__Node__Group_3__2__Impl rule__Node__Group_3__3 ;
    public final void rule__Node__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:765:1: ( rule__Node__Group_3__2__Impl rule__Node__Group_3__3 )
            // InternalPf.g:766:2: rule__Node__Group_3__2__Impl rule__Node__Group_3__3
            {
            pushFollow(FOLLOW_9);
            rule__Node__Group_3__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group_3__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3__2"


    // $ANTLR start "rule__Node__Group_3__2__Impl"
    // InternalPf.g:773:1: rule__Node__Group_3__2__Impl : ( ( rule__Node__Alternatives_3_2 )* ) ;
    public final void rule__Node__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:777:1: ( ( ( rule__Node__Alternatives_3_2 )* ) )
            // InternalPf.g:778:1: ( ( rule__Node__Alternatives_3_2 )* )
            {
            // InternalPf.g:778:1: ( ( rule__Node__Alternatives_3_2 )* )
            // InternalPf.g:779:2: ( rule__Node__Alternatives_3_2 )*
            {
             before(grammarAccess.getNodeAccess().getAlternatives_3_2()); 
            // InternalPf.g:780:2: ( rule__Node__Alternatives_3_2 )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==29||LA12_0==35) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // InternalPf.g:780:3: rule__Node__Alternatives_3_2
            	    {
            	    pushFollow(FOLLOW_10);
            	    rule__Node__Alternatives_3_2();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

             after(grammarAccess.getNodeAccess().getAlternatives_3_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3__2__Impl"


    // $ANTLR start "rule__Node__Group_3__3"
    // InternalPf.g:788:1: rule__Node__Group_3__3 : rule__Node__Group_3__3__Impl ;
    public final void rule__Node__Group_3__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:792:1: ( rule__Node__Group_3__3__Impl )
            // InternalPf.g:793:2: rule__Node__Group_3__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Node__Group_3__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3__3"


    // $ANTLR start "rule__Node__Group_3__3__Impl"
    // InternalPf.g:799:1: rule__Node__Group_3__3__Impl : ( '}' ) ;
    public final void rule__Node__Group_3__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:803:1: ( ( '}' ) )
            // InternalPf.g:804:1: ( '}' )
            {
            // InternalPf.g:804:1: ( '}' )
            // InternalPf.g:805:2: '}'
            {
             before(grammarAccess.getNodeAccess().getRightCurlyBracketKeyword_3_3()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getNodeAccess().getRightCurlyBracketKeyword_3_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3__3__Impl"


    // $ANTLR start "rule__Node__Group_3_1__0"
    // InternalPf.g:815:1: rule__Node__Group_3_1__0 : rule__Node__Group_3_1__0__Impl rule__Node__Group_3_1__1 ;
    public final void rule__Node__Group_3_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:819:1: ( rule__Node__Group_3_1__0__Impl rule__Node__Group_3_1__1 )
            // InternalPf.g:820:2: rule__Node__Group_3_1__0__Impl rule__Node__Group_3_1__1
            {
            pushFollow(FOLLOW_11);
            rule__Node__Group_3_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group_3_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_1__0"


    // $ANTLR start "rule__Node__Group_3_1__0__Impl"
    // InternalPf.g:827:1: rule__Node__Group_3_1__0__Impl : ( ( rule__Node__HiddenPhenomenaAssignment_3_1_0 ) ) ;
    public final void rule__Node__Group_3_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:831:1: ( ( ( rule__Node__HiddenPhenomenaAssignment_3_1_0 ) ) )
            // InternalPf.g:832:1: ( ( rule__Node__HiddenPhenomenaAssignment_3_1_0 ) )
            {
            // InternalPf.g:832:1: ( ( rule__Node__HiddenPhenomenaAssignment_3_1_0 ) )
            // InternalPf.g:833:2: ( rule__Node__HiddenPhenomenaAssignment_3_1_0 )
            {
             before(grammarAccess.getNodeAccess().getHiddenPhenomenaAssignment_3_1_0()); 
            // InternalPf.g:834:2: ( rule__Node__HiddenPhenomenaAssignment_3_1_0 )
            // InternalPf.g:834:3: rule__Node__HiddenPhenomenaAssignment_3_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Node__HiddenPhenomenaAssignment_3_1_0();

            state._fsp--;


            }

             after(grammarAccess.getNodeAccess().getHiddenPhenomenaAssignment_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_1__0__Impl"


    // $ANTLR start "rule__Node__Group_3_1__1"
    // InternalPf.g:842:1: rule__Node__Group_3_1__1 : rule__Node__Group_3_1__1__Impl ;
    public final void rule__Node__Group_3_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:846:1: ( rule__Node__Group_3_1__1__Impl )
            // InternalPf.g:847:2: rule__Node__Group_3_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Node__Group_3_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_1__1"


    // $ANTLR start "rule__Node__Group_3_1__1__Impl"
    // InternalPf.g:853:1: rule__Node__Group_3_1__1__Impl : ( ( rule__Node__Group_3_1_1__0 )* ) ;
    public final void rule__Node__Group_3_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:857:1: ( ( ( rule__Node__Group_3_1_1__0 )* ) )
            // InternalPf.g:858:1: ( ( rule__Node__Group_3_1_1__0 )* )
            {
            // InternalPf.g:858:1: ( ( rule__Node__Group_3_1_1__0 )* )
            // InternalPf.g:859:2: ( rule__Node__Group_3_1_1__0 )*
            {
             before(grammarAccess.getNodeAccess().getGroup_3_1_1()); 
            // InternalPf.g:860:2: ( rule__Node__Group_3_1_1__0 )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==34) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalPf.g:860:3: rule__Node__Group_3_1_1__0
            	    {
            	    pushFollow(FOLLOW_12);
            	    rule__Node__Group_3_1_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

             after(grammarAccess.getNodeAccess().getGroup_3_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_1__1__Impl"


    // $ANTLR start "rule__Node__Group_3_1_1__0"
    // InternalPf.g:869:1: rule__Node__Group_3_1_1__0 : rule__Node__Group_3_1_1__0__Impl rule__Node__Group_3_1_1__1 ;
    public final void rule__Node__Group_3_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:873:1: ( rule__Node__Group_3_1_1__0__Impl rule__Node__Group_3_1_1__1 )
            // InternalPf.g:874:2: rule__Node__Group_3_1_1__0__Impl rule__Node__Group_3_1_1__1
            {
            pushFollow(FOLLOW_13);
            rule__Node__Group_3_1_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group_3_1_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_1_1__0"


    // $ANTLR start "rule__Node__Group_3_1_1__0__Impl"
    // InternalPf.g:881:1: rule__Node__Group_3_1_1__0__Impl : ( ',' ) ;
    public final void rule__Node__Group_3_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:885:1: ( ( ',' ) )
            // InternalPf.g:886:1: ( ',' )
            {
            // InternalPf.g:886:1: ( ',' )
            // InternalPf.g:887:2: ','
            {
             before(grammarAccess.getNodeAccess().getCommaKeyword_3_1_1_0()); 
            match(input,34,FOLLOW_2); 
             after(grammarAccess.getNodeAccess().getCommaKeyword_3_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_1_1__0__Impl"


    // $ANTLR start "rule__Node__Group_3_1_1__1"
    // InternalPf.g:896:1: rule__Node__Group_3_1_1__1 : rule__Node__Group_3_1_1__1__Impl ;
    public final void rule__Node__Group_3_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:900:1: ( rule__Node__Group_3_1_1__1__Impl )
            // InternalPf.g:901:2: rule__Node__Group_3_1_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Node__Group_3_1_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_1_1__1"


    // $ANTLR start "rule__Node__Group_3_1_1__1__Impl"
    // InternalPf.g:907:1: rule__Node__Group_3_1_1__1__Impl : ( ( rule__Node__HiddenPhenomenaAssignment_3_1_1_1 ) ) ;
    public final void rule__Node__Group_3_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:911:1: ( ( ( rule__Node__HiddenPhenomenaAssignment_3_1_1_1 ) ) )
            // InternalPf.g:912:1: ( ( rule__Node__HiddenPhenomenaAssignment_3_1_1_1 ) )
            {
            // InternalPf.g:912:1: ( ( rule__Node__HiddenPhenomenaAssignment_3_1_1_1 ) )
            // InternalPf.g:913:2: ( rule__Node__HiddenPhenomenaAssignment_3_1_1_1 )
            {
             before(grammarAccess.getNodeAccess().getHiddenPhenomenaAssignment_3_1_1_1()); 
            // InternalPf.g:914:2: ( rule__Node__HiddenPhenomenaAssignment_3_1_1_1 )
            // InternalPf.g:914:3: rule__Node__HiddenPhenomenaAssignment_3_1_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Node__HiddenPhenomenaAssignment_3_1_1_1();

            state._fsp--;


            }

             after(grammarAccess.getNodeAccess().getHiddenPhenomenaAssignment_3_1_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_1_1__1__Impl"


    // $ANTLR start "rule__Node__Group_3_2_1__0"
    // InternalPf.g:923:1: rule__Node__Group_3_2_1__0 : rule__Node__Group_3_2_1__0__Impl rule__Node__Group_3_2_1__1 ;
    public final void rule__Node__Group_3_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:927:1: ( rule__Node__Group_3_2_1__0__Impl rule__Node__Group_3_2_1__1 )
            // InternalPf.g:928:2: rule__Node__Group_3_2_1__0__Impl rule__Node__Group_3_2_1__1
            {
            pushFollow(FOLLOW_14);
            rule__Node__Group_3_2_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group_3_2_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_1__0"


    // $ANTLR start "rule__Node__Group_3_2_1__0__Impl"
    // InternalPf.g:935:1: rule__Node__Group_3_2_1__0__Impl : ( 'see' ) ;
    public final void rule__Node__Group_3_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:939:1: ( ( 'see' ) )
            // InternalPf.g:940:1: ( 'see' )
            {
            // InternalPf.g:940:1: ( 'see' )
            // InternalPf.g:941:2: 'see'
            {
             before(grammarAccess.getNodeAccess().getSeeKeyword_3_2_1_0()); 
            match(input,35,FOLLOW_2); 
             after(grammarAccess.getNodeAccess().getSeeKeyword_3_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_1__0__Impl"


    // $ANTLR start "rule__Node__Group_3_2_1__1"
    // InternalPf.g:950:1: rule__Node__Group_3_2_1__1 : rule__Node__Group_3_2_1__1__Impl rule__Node__Group_3_2_1__2 ;
    public final void rule__Node__Group_3_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:954:1: ( rule__Node__Group_3_2_1__1__Impl rule__Node__Group_3_2_1__2 )
            // InternalPf.g:955:2: rule__Node__Group_3_2_1__1__Impl rule__Node__Group_3_2_1__2
            {
            pushFollow(FOLLOW_3);
            rule__Node__Group_3_2_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group_3_2_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_1__1"


    // $ANTLR start "rule__Node__Group_3_2_1__1__Impl"
    // InternalPf.g:962:1: rule__Node__Group_3_2_1__1__Impl : ( 'domain' ) ;
    public final void rule__Node__Group_3_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:966:1: ( ( 'domain' ) )
            // InternalPf.g:967:1: ( 'domain' )
            {
            // InternalPf.g:967:1: ( 'domain' )
            // InternalPf.g:968:2: 'domain'
            {
             before(grammarAccess.getNodeAccess().getDomainKeyword_3_2_1_1()); 
            match(input,36,FOLLOW_2); 
             after(grammarAccess.getNodeAccess().getDomainKeyword_3_2_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_1__1__Impl"


    // $ANTLR start "rule__Node__Group_3_2_1__2"
    // InternalPf.g:977:1: rule__Node__Group_3_2_1__2 : rule__Node__Group_3_2_1__2__Impl ;
    public final void rule__Node__Group_3_2_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:981:1: ( rule__Node__Group_3_2_1__2__Impl )
            // InternalPf.g:982:2: rule__Node__Group_3_2_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Node__Group_3_2_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_1__2"


    // $ANTLR start "rule__Node__Group_3_2_1__2__Impl"
    // InternalPf.g:988:1: rule__Node__Group_3_2_1__2__Impl : ( ( rule__Node__ProblemNodeRefAssignment_3_2_1_2 ) ) ;
    public final void rule__Node__Group_3_2_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:992:1: ( ( ( rule__Node__ProblemNodeRefAssignment_3_2_1_2 ) ) )
            // InternalPf.g:993:1: ( ( rule__Node__ProblemNodeRefAssignment_3_2_1_2 ) )
            {
            // InternalPf.g:993:1: ( ( rule__Node__ProblemNodeRefAssignment_3_2_1_2 ) )
            // InternalPf.g:994:2: ( rule__Node__ProblemNodeRefAssignment_3_2_1_2 )
            {
             before(grammarAccess.getNodeAccess().getProblemNodeRefAssignment_3_2_1_2()); 
            // InternalPf.g:995:2: ( rule__Node__ProblemNodeRefAssignment_3_2_1_2 )
            // InternalPf.g:995:3: rule__Node__ProblemNodeRefAssignment_3_2_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Node__ProblemNodeRefAssignment_3_2_1_2();

            state._fsp--;


            }

             after(grammarAccess.getNodeAccess().getProblemNodeRefAssignment_3_2_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_1__2__Impl"


    // $ANTLR start "rule__Node__Group_3_2_2__0"
    // InternalPf.g:1004:1: rule__Node__Group_3_2_2__0 : rule__Node__Group_3_2_2__0__Impl rule__Node__Group_3_2_2__1 ;
    public final void rule__Node__Group_3_2_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1008:1: ( rule__Node__Group_3_2_2__0__Impl rule__Node__Group_3_2_2__1 )
            // InternalPf.g:1009:2: rule__Node__Group_3_2_2__0__Impl rule__Node__Group_3_2_2__1
            {
            pushFollow(FOLLOW_15);
            rule__Node__Group_3_2_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group_3_2_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_2__0"


    // $ANTLR start "rule__Node__Group_3_2_2__0__Impl"
    // InternalPf.g:1016:1: rule__Node__Group_3_2_2__0__Impl : ( 'see' ) ;
    public final void rule__Node__Group_3_2_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1020:1: ( ( 'see' ) )
            // InternalPf.g:1021:1: ( 'see' )
            {
            // InternalPf.g:1021:1: ( 'see' )
            // InternalPf.g:1022:2: 'see'
            {
             before(grammarAccess.getNodeAccess().getSeeKeyword_3_2_2_0()); 
            match(input,35,FOLLOW_2); 
             after(grammarAccess.getNodeAccess().getSeeKeyword_3_2_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_2__0__Impl"


    // $ANTLR start "rule__Node__Group_3_2_2__1"
    // InternalPf.g:1031:1: rule__Node__Group_3_2_2__1 : rule__Node__Group_3_2_2__1__Impl rule__Node__Group_3_2_2__2 ;
    public final void rule__Node__Group_3_2_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1035:1: ( rule__Node__Group_3_2_2__1__Impl rule__Node__Group_3_2_2__2 )
            // InternalPf.g:1036:2: rule__Node__Group_3_2_2__1__Impl rule__Node__Group_3_2_2__2
            {
            pushFollow(FOLLOW_3);
            rule__Node__Group_3_2_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group_3_2_2__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_2__1"


    // $ANTLR start "rule__Node__Group_3_2_2__1__Impl"
    // InternalPf.g:1043:1: rule__Node__Group_3_2_2__1__Impl : ( 'problem' ) ;
    public final void rule__Node__Group_3_2_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1047:1: ( ( 'problem' ) )
            // InternalPf.g:1048:1: ( 'problem' )
            {
            // InternalPf.g:1048:1: ( 'problem' )
            // InternalPf.g:1049:2: 'problem'
            {
             before(grammarAccess.getNodeAccess().getProblemKeyword_3_2_2_1()); 
            match(input,37,FOLLOW_2); 
             after(grammarAccess.getNodeAccess().getProblemKeyword_3_2_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_2__1__Impl"


    // $ANTLR start "rule__Node__Group_3_2_2__2"
    // InternalPf.g:1058:1: rule__Node__Group_3_2_2__2 : rule__Node__Group_3_2_2__2__Impl ;
    public final void rule__Node__Group_3_2_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1062:1: ( rule__Node__Group_3_2_2__2__Impl )
            // InternalPf.g:1063:2: rule__Node__Group_3_2_2__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Node__Group_3_2_2__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_2__2"


    // $ANTLR start "rule__Node__Group_3_2_2__2__Impl"
    // InternalPf.g:1069:1: rule__Node__Group_3_2_2__2__Impl : ( ( rule__Node__ProblemRefAssignment_3_2_2_2 ) ) ;
    public final void rule__Node__Group_3_2_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1073:1: ( ( ( rule__Node__ProblemRefAssignment_3_2_2_2 ) ) )
            // InternalPf.g:1074:1: ( ( rule__Node__ProblemRefAssignment_3_2_2_2 ) )
            {
            // InternalPf.g:1074:1: ( ( rule__Node__ProblemRefAssignment_3_2_2_2 ) )
            // InternalPf.g:1075:2: ( rule__Node__ProblemRefAssignment_3_2_2_2 )
            {
             before(grammarAccess.getNodeAccess().getProblemRefAssignment_3_2_2_2()); 
            // InternalPf.g:1076:2: ( rule__Node__ProblemRefAssignment_3_2_2_2 )
            // InternalPf.g:1076:3: rule__Node__ProblemRefAssignment_3_2_2_2
            {
            pushFollow(FOLLOW_2);
            rule__Node__ProblemRefAssignment_3_2_2_2();

            state._fsp--;


            }

             after(grammarAccess.getNodeAccess().getProblemRefAssignment_3_2_2_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_2__2__Impl"


    // $ANTLR start "rule__Node__Group_3_2_3__0"
    // InternalPf.g:1085:1: rule__Node__Group_3_2_3__0 : rule__Node__Group_3_2_3__0__Impl rule__Node__Group_3_2_3__1 ;
    public final void rule__Node__Group_3_2_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1089:1: ( rule__Node__Group_3_2_3__0__Impl rule__Node__Group_3_2_3__1 )
            // InternalPf.g:1090:2: rule__Node__Group_3_2_3__0__Impl rule__Node__Group_3_2_3__1
            {
            pushFollow(FOLLOW_16);
            rule__Node__Group_3_2_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Node__Group_3_2_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_3__0"


    // $ANTLR start "rule__Node__Group_3_2_3__0__Impl"
    // InternalPf.g:1097:1: rule__Node__Group_3_2_3__0__Impl : ( 'see' ) ;
    public final void rule__Node__Group_3_2_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1101:1: ( ( 'see' ) )
            // InternalPf.g:1102:1: ( 'see' )
            {
            // InternalPf.g:1102:1: ( 'see' )
            // InternalPf.g:1103:2: 'see'
            {
             before(grammarAccess.getNodeAccess().getSeeKeyword_3_2_3_0()); 
            match(input,35,FOLLOW_2); 
             after(grammarAccess.getNodeAccess().getSeeKeyword_3_2_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_3__0__Impl"


    // $ANTLR start "rule__Node__Group_3_2_3__1"
    // InternalPf.g:1112:1: rule__Node__Group_3_2_3__1 : rule__Node__Group_3_2_3__1__Impl ;
    public final void rule__Node__Group_3_2_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1116:1: ( rule__Node__Group_3_2_3__1__Impl )
            // InternalPf.g:1117:2: rule__Node__Group_3_2_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Node__Group_3_2_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_3__1"


    // $ANTLR start "rule__Node__Group_3_2_3__1__Impl"
    // InternalPf.g:1123:1: rule__Node__Group_3_2_3__1__Impl : ( ( rule__Node__HrefAssignment_3_2_3_1 ) ) ;
    public final void rule__Node__Group_3_2_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1127:1: ( ( ( rule__Node__HrefAssignment_3_2_3_1 ) ) )
            // InternalPf.g:1128:1: ( ( rule__Node__HrefAssignment_3_2_3_1 ) )
            {
            // InternalPf.g:1128:1: ( ( rule__Node__HrefAssignment_3_2_3_1 ) )
            // InternalPf.g:1129:2: ( rule__Node__HrefAssignment_3_2_3_1 )
            {
             before(grammarAccess.getNodeAccess().getHrefAssignment_3_2_3_1()); 
            // InternalPf.g:1130:2: ( rule__Node__HrefAssignment_3_2_3_1 )
            // InternalPf.g:1130:3: rule__Node__HrefAssignment_3_2_3_1
            {
            pushFollow(FOLLOW_2);
            rule__Node__HrefAssignment_3_2_3_1();

            state._fsp--;


            }

             after(grammarAccess.getNodeAccess().getHrefAssignment_3_2_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__Group_3_2_3__1__Impl"


    // $ANTLR start "rule__Phenomenon__Group__0"
    // InternalPf.g:1139:1: rule__Phenomenon__Group__0 : rule__Phenomenon__Group__0__Impl rule__Phenomenon__Group__1 ;
    public final void rule__Phenomenon__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1143:1: ( rule__Phenomenon__Group__0__Impl rule__Phenomenon__Group__1 )
            // InternalPf.g:1144:2: rule__Phenomenon__Group__0__Impl rule__Phenomenon__Group__1
            {
            pushFollow(FOLLOW_13);
            rule__Phenomenon__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Phenomenon__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__Group__0"


    // $ANTLR start "rule__Phenomenon__Group__0__Impl"
    // InternalPf.g:1151:1: rule__Phenomenon__Group__0__Impl : ( ( rule__Phenomenon__TypeAssignment_0 )? ) ;
    public final void rule__Phenomenon__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1155:1: ( ( ( rule__Phenomenon__TypeAssignment_0 )? ) )
            // InternalPf.g:1156:1: ( ( rule__Phenomenon__TypeAssignment_0 )? )
            {
            // InternalPf.g:1156:1: ( ( rule__Phenomenon__TypeAssignment_0 )? )
            // InternalPf.g:1157:2: ( rule__Phenomenon__TypeAssignment_0 )?
            {
             before(grammarAccess.getPhenomenonAccess().getTypeAssignment_0()); 
            // InternalPf.g:1158:2: ( rule__Phenomenon__TypeAssignment_0 )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=20 && LA14_0<=23)) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalPf.g:1158:3: rule__Phenomenon__TypeAssignment_0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Phenomenon__TypeAssignment_0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPhenomenonAccess().getTypeAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__Group__0__Impl"


    // $ANTLR start "rule__Phenomenon__Group__1"
    // InternalPf.g:1166:1: rule__Phenomenon__Group__1 : rule__Phenomenon__Group__1__Impl rule__Phenomenon__Group__2 ;
    public final void rule__Phenomenon__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1170:1: ( rule__Phenomenon__Group__1__Impl rule__Phenomenon__Group__2 )
            // InternalPf.g:1171:2: rule__Phenomenon__Group__1__Impl rule__Phenomenon__Group__2
            {
            pushFollow(FOLLOW_13);
            rule__Phenomenon__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Phenomenon__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__Group__1"


    // $ANTLR start "rule__Phenomenon__Group__1__Impl"
    // InternalPf.g:1178:1: rule__Phenomenon__Group__1__Impl : ( ( rule__Phenomenon__IsControlledAssignment_1 )? ) ;
    public final void rule__Phenomenon__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1182:1: ( ( ( rule__Phenomenon__IsControlledAssignment_1 )? ) )
            // InternalPf.g:1183:1: ( ( rule__Phenomenon__IsControlledAssignment_1 )? )
            {
            // InternalPf.g:1183:1: ( ( rule__Phenomenon__IsControlledAssignment_1 )? )
            // InternalPf.g:1184:2: ( rule__Phenomenon__IsControlledAssignment_1 )?
            {
             before(grammarAccess.getPhenomenonAccess().getIsControlledAssignment_1()); 
            // InternalPf.g:1185:2: ( rule__Phenomenon__IsControlledAssignment_1 )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==38) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalPf.g:1185:3: rule__Phenomenon__IsControlledAssignment_1
                    {
                    pushFollow(FOLLOW_2);
                    rule__Phenomenon__IsControlledAssignment_1();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPhenomenonAccess().getIsControlledAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__Group__1__Impl"


    // $ANTLR start "rule__Phenomenon__Group__2"
    // InternalPf.g:1193:1: rule__Phenomenon__Group__2 : rule__Phenomenon__Group__2__Impl rule__Phenomenon__Group__3 ;
    public final void rule__Phenomenon__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1197:1: ( rule__Phenomenon__Group__2__Impl rule__Phenomenon__Group__3 )
            // InternalPf.g:1198:2: rule__Phenomenon__Group__2__Impl rule__Phenomenon__Group__3
            {
            pushFollow(FOLLOW_8);
            rule__Phenomenon__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Phenomenon__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__Group__2"


    // $ANTLR start "rule__Phenomenon__Group__2__Impl"
    // InternalPf.g:1205:1: rule__Phenomenon__Group__2__Impl : ( ( rule__Phenomenon__NameAssignment_2 ) ) ;
    public final void rule__Phenomenon__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1209:1: ( ( ( rule__Phenomenon__NameAssignment_2 ) ) )
            // InternalPf.g:1210:1: ( ( rule__Phenomenon__NameAssignment_2 ) )
            {
            // InternalPf.g:1210:1: ( ( rule__Phenomenon__NameAssignment_2 ) )
            // InternalPf.g:1211:2: ( rule__Phenomenon__NameAssignment_2 )
            {
             before(grammarAccess.getPhenomenonAccess().getNameAssignment_2()); 
            // InternalPf.g:1212:2: ( rule__Phenomenon__NameAssignment_2 )
            // InternalPf.g:1212:3: rule__Phenomenon__NameAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__Phenomenon__NameAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getPhenomenonAccess().getNameAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__Group__2__Impl"


    // $ANTLR start "rule__Phenomenon__Group__3"
    // InternalPf.g:1220:1: rule__Phenomenon__Group__3 : rule__Phenomenon__Group__3__Impl ;
    public final void rule__Phenomenon__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1224:1: ( rule__Phenomenon__Group__3__Impl )
            // InternalPf.g:1225:2: rule__Phenomenon__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Phenomenon__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__Group__3"


    // $ANTLR start "rule__Phenomenon__Group__3__Impl"
    // InternalPf.g:1231:1: rule__Phenomenon__Group__3__Impl : ( ( rule__Phenomenon__Group_3__0 )? ) ;
    public final void rule__Phenomenon__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1235:1: ( ( ( rule__Phenomenon__Group_3__0 )? ) )
            // InternalPf.g:1236:1: ( ( rule__Phenomenon__Group_3__0 )? )
            {
            // InternalPf.g:1236:1: ( ( rule__Phenomenon__Group_3__0 )? )
            // InternalPf.g:1237:2: ( rule__Phenomenon__Group_3__0 )?
            {
             before(grammarAccess.getPhenomenonAccess().getGroup_3()); 
            // InternalPf.g:1238:2: ( rule__Phenomenon__Group_3__0 )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_STRING||LA16_0==31) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalPf.g:1238:3: rule__Phenomenon__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Phenomenon__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getPhenomenonAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__Group__3__Impl"


    // $ANTLR start "rule__Phenomenon__Group_3__0"
    // InternalPf.g:1247:1: rule__Phenomenon__Group_3__0 : rule__Phenomenon__Group_3__0__Impl rule__Phenomenon__Group_3__1 ;
    public final void rule__Phenomenon__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1251:1: ( rule__Phenomenon__Group_3__0__Impl rule__Phenomenon__Group_3__1 )
            // InternalPf.g:1252:2: rule__Phenomenon__Group_3__0__Impl rule__Phenomenon__Group_3__1
            {
            pushFollow(FOLLOW_8);
            rule__Phenomenon__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Phenomenon__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__Group_3__0"


    // $ANTLR start "rule__Phenomenon__Group_3__0__Impl"
    // InternalPf.g:1259:1: rule__Phenomenon__Group_3__0__Impl : ( ( ':' )? ) ;
    public final void rule__Phenomenon__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1263:1: ( ( ( ':' )? ) )
            // InternalPf.g:1264:1: ( ( ':' )? )
            {
            // InternalPf.g:1264:1: ( ( ':' )? )
            // InternalPf.g:1265:2: ( ':' )?
            {
             before(grammarAccess.getPhenomenonAccess().getColonKeyword_3_0()); 
            // InternalPf.g:1266:2: ( ':' )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==31) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalPf.g:1266:3: ':'
                    {
                    match(input,31,FOLLOW_2); 

                    }
                    break;

            }

             after(grammarAccess.getPhenomenonAccess().getColonKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__Group_3__0__Impl"


    // $ANTLR start "rule__Phenomenon__Group_3__1"
    // InternalPf.g:1274:1: rule__Phenomenon__Group_3__1 : rule__Phenomenon__Group_3__1__Impl ;
    public final void rule__Phenomenon__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1278:1: ( rule__Phenomenon__Group_3__1__Impl )
            // InternalPf.g:1279:2: rule__Phenomenon__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Phenomenon__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__Group_3__1"


    // $ANTLR start "rule__Phenomenon__Group_3__1__Impl"
    // InternalPf.g:1285:1: rule__Phenomenon__Group_3__1__Impl : ( ( rule__Phenomenon__DescriptionAssignment_3_1 ) ) ;
    public final void rule__Phenomenon__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1289:1: ( ( ( rule__Phenomenon__DescriptionAssignment_3_1 ) ) )
            // InternalPf.g:1290:1: ( ( rule__Phenomenon__DescriptionAssignment_3_1 ) )
            {
            // InternalPf.g:1290:1: ( ( rule__Phenomenon__DescriptionAssignment_3_1 ) )
            // InternalPf.g:1291:2: ( rule__Phenomenon__DescriptionAssignment_3_1 )
            {
             before(grammarAccess.getPhenomenonAccess().getDescriptionAssignment_3_1()); 
            // InternalPf.g:1292:2: ( rule__Phenomenon__DescriptionAssignment_3_1 )
            // InternalPf.g:1292:3: rule__Phenomenon__DescriptionAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__Phenomenon__DescriptionAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getPhenomenonAccess().getDescriptionAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__Group_3__1__Impl"


    // $ANTLR start "rule__Link__Group__0"
    // InternalPf.g:1301:1: rule__Link__Group__0 : rule__Link__Group__0__Impl rule__Link__Group__1 ;
    public final void rule__Link__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1305:1: ( rule__Link__Group__0__Impl rule__Link__Group__1 )
            // InternalPf.g:1306:2: rule__Link__Group__0__Impl rule__Link__Group__1
            {
            pushFollow(FOLLOW_17);
            rule__Link__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Link__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__0"


    // $ANTLR start "rule__Link__Group__0__Impl"
    // InternalPf.g:1313:1: rule__Link__Group__0__Impl : ( ( rule__Link__FromAssignment_0 ) ) ;
    public final void rule__Link__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1317:1: ( ( ( rule__Link__FromAssignment_0 ) ) )
            // InternalPf.g:1318:1: ( ( rule__Link__FromAssignment_0 ) )
            {
            // InternalPf.g:1318:1: ( ( rule__Link__FromAssignment_0 ) )
            // InternalPf.g:1319:2: ( rule__Link__FromAssignment_0 )
            {
             before(grammarAccess.getLinkAccess().getFromAssignment_0()); 
            // InternalPf.g:1320:2: ( rule__Link__FromAssignment_0 )
            // InternalPf.g:1320:3: rule__Link__FromAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__Link__FromAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getLinkAccess().getFromAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__0__Impl"


    // $ANTLR start "rule__Link__Group__1"
    // InternalPf.g:1328:1: rule__Link__Group__1 : rule__Link__Group__1__Impl rule__Link__Group__2 ;
    public final void rule__Link__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1332:1: ( rule__Link__Group__1__Impl rule__Link__Group__2 )
            // InternalPf.g:1333:2: rule__Link__Group__1__Impl rule__Link__Group__2
            {
            pushFollow(FOLLOW_3);
            rule__Link__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Link__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__1"


    // $ANTLR start "rule__Link__Group__1__Impl"
    // InternalPf.g:1340:1: rule__Link__Group__1__Impl : ( ( rule__Link__TypeAssignment_1 ) ) ;
    public final void rule__Link__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1344:1: ( ( ( rule__Link__TypeAssignment_1 ) ) )
            // InternalPf.g:1345:1: ( ( rule__Link__TypeAssignment_1 ) )
            {
            // InternalPf.g:1345:1: ( ( rule__Link__TypeAssignment_1 ) )
            // InternalPf.g:1346:2: ( rule__Link__TypeAssignment_1 )
            {
             before(grammarAccess.getLinkAccess().getTypeAssignment_1()); 
            // InternalPf.g:1347:2: ( rule__Link__TypeAssignment_1 )
            // InternalPf.g:1347:3: rule__Link__TypeAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Link__TypeAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getLinkAccess().getTypeAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__1__Impl"


    // $ANTLR start "rule__Link__Group__2"
    // InternalPf.g:1355:1: rule__Link__Group__2 : rule__Link__Group__2__Impl rule__Link__Group__3 ;
    public final void rule__Link__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1359:1: ( rule__Link__Group__2__Impl rule__Link__Group__3 )
            // InternalPf.g:1360:2: rule__Link__Group__2__Impl rule__Link__Group__3
            {
            pushFollow(FOLLOW_7);
            rule__Link__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Link__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__2"


    // $ANTLR start "rule__Link__Group__2__Impl"
    // InternalPf.g:1367:1: rule__Link__Group__2__Impl : ( ( rule__Link__ToAssignment_2 ) ) ;
    public final void rule__Link__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1371:1: ( ( ( rule__Link__ToAssignment_2 ) ) )
            // InternalPf.g:1372:1: ( ( rule__Link__ToAssignment_2 ) )
            {
            // InternalPf.g:1372:1: ( ( rule__Link__ToAssignment_2 ) )
            // InternalPf.g:1373:2: ( rule__Link__ToAssignment_2 )
            {
             before(grammarAccess.getLinkAccess().getToAssignment_2()); 
            // InternalPf.g:1374:2: ( rule__Link__ToAssignment_2 )
            // InternalPf.g:1374:3: rule__Link__ToAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__Link__ToAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getLinkAccess().getToAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__2__Impl"


    // $ANTLR start "rule__Link__Group__3"
    // InternalPf.g:1382:1: rule__Link__Group__3 : rule__Link__Group__3__Impl rule__Link__Group__4 ;
    public final void rule__Link__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1386:1: ( rule__Link__Group__3__Impl rule__Link__Group__4 )
            // InternalPf.g:1387:2: rule__Link__Group__3__Impl rule__Link__Group__4
            {
            pushFollow(FOLLOW_7);
            rule__Link__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Link__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__3"


    // $ANTLR start "rule__Link__Group__3__Impl"
    // InternalPf.g:1394:1: rule__Link__Group__3__Impl : ( ( rule__Link__Group_3__0 )? ) ;
    public final void rule__Link__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1398:1: ( ( ( rule__Link__Group_3__0 )? ) )
            // InternalPf.g:1399:1: ( ( rule__Link__Group_3__0 )? )
            {
            // InternalPf.g:1399:1: ( ( rule__Link__Group_3__0 )? )
            // InternalPf.g:1400:2: ( rule__Link__Group_3__0 )?
            {
             before(grammarAccess.getLinkAccess().getGroup_3()); 
            // InternalPf.g:1401:2: ( rule__Link__Group_3__0 )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==32) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalPf.g:1401:3: rule__Link__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Link__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLinkAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__3__Impl"


    // $ANTLR start "rule__Link__Group__4"
    // InternalPf.g:1409:1: rule__Link__Group__4 : rule__Link__Group__4__Impl ;
    public final void rule__Link__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1413:1: ( rule__Link__Group__4__Impl )
            // InternalPf.g:1414:2: rule__Link__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Link__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__4"


    // $ANTLR start "rule__Link__Group__4__Impl"
    // InternalPf.g:1420:1: rule__Link__Group__4__Impl : ( ( rule__Link__Group_4__0 )? ) ;
    public final void rule__Link__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1424:1: ( ( ( rule__Link__Group_4__0 )? ) )
            // InternalPf.g:1425:1: ( ( rule__Link__Group_4__0 )? )
            {
            // InternalPf.g:1425:1: ( ( rule__Link__Group_4__0 )? )
            // InternalPf.g:1426:2: ( rule__Link__Group_4__0 )?
            {
             before(grammarAccess.getLinkAccess().getGroup_4()); 
            // InternalPf.g:1427:2: ( rule__Link__Group_4__0 )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==RULE_STRING||LA19_0==31) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalPf.g:1427:3: rule__Link__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Link__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getLinkAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group__4__Impl"


    // $ANTLR start "rule__Link__Group_3__0"
    // InternalPf.g:1436:1: rule__Link__Group_3__0 : rule__Link__Group_3__0__Impl rule__Link__Group_3__1 ;
    public final void rule__Link__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1440:1: ( rule__Link__Group_3__0__Impl rule__Link__Group_3__1 )
            // InternalPf.g:1441:2: rule__Link__Group_3__0__Impl rule__Link__Group_3__1
            {
            pushFollow(FOLLOW_13);
            rule__Link__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Link__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_3__0"


    // $ANTLR start "rule__Link__Group_3__0__Impl"
    // InternalPf.g:1448:1: rule__Link__Group_3__0__Impl : ( '{' ) ;
    public final void rule__Link__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1452:1: ( ( '{' ) )
            // InternalPf.g:1453:1: ( '{' )
            {
            // InternalPf.g:1453:1: ( '{' )
            // InternalPf.g:1454:2: '{'
            {
             before(grammarAccess.getLinkAccess().getLeftCurlyBracketKeyword_3_0()); 
            match(input,32,FOLLOW_2); 
             after(grammarAccess.getLinkAccess().getLeftCurlyBracketKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_3__0__Impl"


    // $ANTLR start "rule__Link__Group_3__1"
    // InternalPf.g:1463:1: rule__Link__Group_3__1 : rule__Link__Group_3__1__Impl rule__Link__Group_3__2 ;
    public final void rule__Link__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1467:1: ( rule__Link__Group_3__1__Impl rule__Link__Group_3__2 )
            // InternalPf.g:1468:2: rule__Link__Group_3__1__Impl rule__Link__Group_3__2
            {
            pushFollow(FOLLOW_18);
            rule__Link__Group_3__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Link__Group_3__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_3__1"


    // $ANTLR start "rule__Link__Group_3__1__Impl"
    // InternalPf.g:1475:1: rule__Link__Group_3__1__Impl : ( ( rule__Link__PhenomenaAssignment_3_1 ) ) ;
    public final void rule__Link__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1479:1: ( ( ( rule__Link__PhenomenaAssignment_3_1 ) ) )
            // InternalPf.g:1480:1: ( ( rule__Link__PhenomenaAssignment_3_1 ) )
            {
            // InternalPf.g:1480:1: ( ( rule__Link__PhenomenaAssignment_3_1 ) )
            // InternalPf.g:1481:2: ( rule__Link__PhenomenaAssignment_3_1 )
            {
             before(grammarAccess.getLinkAccess().getPhenomenaAssignment_3_1()); 
            // InternalPf.g:1482:2: ( rule__Link__PhenomenaAssignment_3_1 )
            // InternalPf.g:1482:3: rule__Link__PhenomenaAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__Link__PhenomenaAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getLinkAccess().getPhenomenaAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_3__1__Impl"


    // $ANTLR start "rule__Link__Group_3__2"
    // InternalPf.g:1490:1: rule__Link__Group_3__2 : rule__Link__Group_3__2__Impl rule__Link__Group_3__3 ;
    public final void rule__Link__Group_3__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1494:1: ( rule__Link__Group_3__2__Impl rule__Link__Group_3__3 )
            // InternalPf.g:1495:2: rule__Link__Group_3__2__Impl rule__Link__Group_3__3
            {
            pushFollow(FOLLOW_18);
            rule__Link__Group_3__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Link__Group_3__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_3__2"


    // $ANTLR start "rule__Link__Group_3__2__Impl"
    // InternalPf.g:1502:1: rule__Link__Group_3__2__Impl : ( ( rule__Link__Group_3_2__0 )* ) ;
    public final void rule__Link__Group_3__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1506:1: ( ( ( rule__Link__Group_3_2__0 )* ) )
            // InternalPf.g:1507:1: ( ( rule__Link__Group_3_2__0 )* )
            {
            // InternalPf.g:1507:1: ( ( rule__Link__Group_3_2__0 )* )
            // InternalPf.g:1508:2: ( rule__Link__Group_3_2__0 )*
            {
             before(grammarAccess.getLinkAccess().getGroup_3_2()); 
            // InternalPf.g:1509:2: ( rule__Link__Group_3_2__0 )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==34) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // InternalPf.g:1509:3: rule__Link__Group_3_2__0
            	    {
            	    pushFollow(FOLLOW_12);
            	    rule__Link__Group_3_2__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

             after(grammarAccess.getLinkAccess().getGroup_3_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_3__2__Impl"


    // $ANTLR start "rule__Link__Group_3__3"
    // InternalPf.g:1517:1: rule__Link__Group_3__3 : rule__Link__Group_3__3__Impl ;
    public final void rule__Link__Group_3__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1521:1: ( rule__Link__Group_3__3__Impl )
            // InternalPf.g:1522:2: rule__Link__Group_3__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Link__Group_3__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_3__3"


    // $ANTLR start "rule__Link__Group_3__3__Impl"
    // InternalPf.g:1528:1: rule__Link__Group_3__3__Impl : ( '}' ) ;
    public final void rule__Link__Group_3__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1532:1: ( ( '}' ) )
            // InternalPf.g:1533:1: ( '}' )
            {
            // InternalPf.g:1533:1: ( '}' )
            // InternalPf.g:1534:2: '}'
            {
             before(grammarAccess.getLinkAccess().getRightCurlyBracketKeyword_3_3()); 
            match(input,33,FOLLOW_2); 
             after(grammarAccess.getLinkAccess().getRightCurlyBracketKeyword_3_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_3__3__Impl"


    // $ANTLR start "rule__Link__Group_3_2__0"
    // InternalPf.g:1544:1: rule__Link__Group_3_2__0 : rule__Link__Group_3_2__0__Impl rule__Link__Group_3_2__1 ;
    public final void rule__Link__Group_3_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1548:1: ( rule__Link__Group_3_2__0__Impl rule__Link__Group_3_2__1 )
            // InternalPf.g:1549:2: rule__Link__Group_3_2__0__Impl rule__Link__Group_3_2__1
            {
            pushFollow(FOLLOW_13);
            rule__Link__Group_3_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Link__Group_3_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_3_2__0"


    // $ANTLR start "rule__Link__Group_3_2__0__Impl"
    // InternalPf.g:1556:1: rule__Link__Group_3_2__0__Impl : ( ',' ) ;
    public final void rule__Link__Group_3_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1560:1: ( ( ',' ) )
            // InternalPf.g:1561:1: ( ',' )
            {
            // InternalPf.g:1561:1: ( ',' )
            // InternalPf.g:1562:2: ','
            {
             before(grammarAccess.getLinkAccess().getCommaKeyword_3_2_0()); 
            match(input,34,FOLLOW_2); 
             after(grammarAccess.getLinkAccess().getCommaKeyword_3_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_3_2__0__Impl"


    // $ANTLR start "rule__Link__Group_3_2__1"
    // InternalPf.g:1571:1: rule__Link__Group_3_2__1 : rule__Link__Group_3_2__1__Impl ;
    public final void rule__Link__Group_3_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1575:1: ( rule__Link__Group_3_2__1__Impl )
            // InternalPf.g:1576:2: rule__Link__Group_3_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Link__Group_3_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_3_2__1"


    // $ANTLR start "rule__Link__Group_3_2__1__Impl"
    // InternalPf.g:1582:1: rule__Link__Group_3_2__1__Impl : ( ( rule__Link__PhenomenaAssignment_3_2_1 ) ) ;
    public final void rule__Link__Group_3_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1586:1: ( ( ( rule__Link__PhenomenaAssignment_3_2_1 ) ) )
            // InternalPf.g:1587:1: ( ( rule__Link__PhenomenaAssignment_3_2_1 ) )
            {
            // InternalPf.g:1587:1: ( ( rule__Link__PhenomenaAssignment_3_2_1 ) )
            // InternalPf.g:1588:2: ( rule__Link__PhenomenaAssignment_3_2_1 )
            {
             before(grammarAccess.getLinkAccess().getPhenomenaAssignment_3_2_1()); 
            // InternalPf.g:1589:2: ( rule__Link__PhenomenaAssignment_3_2_1 )
            // InternalPf.g:1589:3: rule__Link__PhenomenaAssignment_3_2_1
            {
            pushFollow(FOLLOW_2);
            rule__Link__PhenomenaAssignment_3_2_1();

            state._fsp--;


            }

             after(grammarAccess.getLinkAccess().getPhenomenaAssignment_3_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_3_2__1__Impl"


    // $ANTLR start "rule__Link__Group_4__0"
    // InternalPf.g:1598:1: rule__Link__Group_4__0 : rule__Link__Group_4__0__Impl rule__Link__Group_4__1 ;
    public final void rule__Link__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1602:1: ( rule__Link__Group_4__0__Impl rule__Link__Group_4__1 )
            // InternalPf.g:1603:2: rule__Link__Group_4__0__Impl rule__Link__Group_4__1
            {
            pushFollow(FOLLOW_8);
            rule__Link__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Link__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_4__0"


    // $ANTLR start "rule__Link__Group_4__0__Impl"
    // InternalPf.g:1610:1: rule__Link__Group_4__0__Impl : ( ( ':' )? ) ;
    public final void rule__Link__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1614:1: ( ( ( ':' )? ) )
            // InternalPf.g:1615:1: ( ( ':' )? )
            {
            // InternalPf.g:1615:1: ( ( ':' )? )
            // InternalPf.g:1616:2: ( ':' )?
            {
             before(grammarAccess.getLinkAccess().getColonKeyword_4_0()); 
            // InternalPf.g:1617:2: ( ':' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==31) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalPf.g:1617:3: ':'
                    {
                    match(input,31,FOLLOW_2); 

                    }
                    break;

            }

             after(grammarAccess.getLinkAccess().getColonKeyword_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_4__0__Impl"


    // $ANTLR start "rule__Link__Group_4__1"
    // InternalPf.g:1625:1: rule__Link__Group_4__1 : rule__Link__Group_4__1__Impl ;
    public final void rule__Link__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1629:1: ( rule__Link__Group_4__1__Impl )
            // InternalPf.g:1630:2: rule__Link__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Link__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_4__1"


    // $ANTLR start "rule__Link__Group_4__1__Impl"
    // InternalPf.g:1636:1: rule__Link__Group_4__1__Impl : ( ( rule__Link__DescriptionAssignment_4_1 ) ) ;
    public final void rule__Link__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1640:1: ( ( ( rule__Link__DescriptionAssignment_4_1 ) ) )
            // InternalPf.g:1641:1: ( ( rule__Link__DescriptionAssignment_4_1 ) )
            {
            // InternalPf.g:1641:1: ( ( rule__Link__DescriptionAssignment_4_1 ) )
            // InternalPf.g:1642:2: ( rule__Link__DescriptionAssignment_4_1 )
            {
             before(grammarAccess.getLinkAccess().getDescriptionAssignment_4_1()); 
            // InternalPf.g:1643:2: ( rule__Link__DescriptionAssignment_4_1 )
            // InternalPf.g:1643:3: rule__Link__DescriptionAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__Link__DescriptionAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getLinkAccess().getDescriptionAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__Group_4__1__Impl"


    // $ANTLR start "rule__ProblemDiagram__NameAssignment_1"
    // InternalPf.g:1652:1: rule__ProblemDiagram__NameAssignment_1 : ( RULE_ID ) ;
    public final void rule__ProblemDiagram__NameAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1656:1: ( ( RULE_ID ) )
            // InternalPf.g:1657:2: ( RULE_ID )
            {
            // InternalPf.g:1657:2: ( RULE_ID )
            // InternalPf.g:1658:3: RULE_ID
            {
             before(grammarAccess.getProblemDiagramAccess().getNameIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getProblemDiagramAccess().getNameIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__NameAssignment_1"


    // $ANTLR start "rule__ProblemDiagram__HighlightAssignment_2_1"
    // InternalPf.g:1667:1: rule__ProblemDiagram__HighlightAssignment_2_1 : ( ( RULE_ID ) ) ;
    public final void rule__ProblemDiagram__HighlightAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1671:1: ( ( ( RULE_ID ) ) )
            // InternalPf.g:1672:2: ( ( RULE_ID ) )
            {
            // InternalPf.g:1672:2: ( ( RULE_ID ) )
            // InternalPf.g:1673:3: ( RULE_ID )
            {
             before(grammarAccess.getProblemDiagramAccess().getHighlightNodeCrossReference_2_1_0()); 
            // InternalPf.g:1674:3: ( RULE_ID )
            // InternalPf.g:1675:4: RULE_ID
            {
             before(grammarAccess.getProblemDiagramAccess().getHighlightNodeIDTerminalRuleCall_2_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getProblemDiagramAccess().getHighlightNodeIDTerminalRuleCall_2_1_0_1()); 

            }

             after(grammarAccess.getProblemDiagramAccess().getHighlightNodeCrossReference_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__HighlightAssignment_2_1"


    // $ANTLR start "rule__ProblemDiagram__NodesAssignment_3_0"
    // InternalPf.g:1686:1: rule__ProblemDiagram__NodesAssignment_3_0 : ( ruleNode ) ;
    public final void rule__ProblemDiagram__NodesAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1690:1: ( ( ruleNode ) )
            // InternalPf.g:1691:2: ( ruleNode )
            {
            // InternalPf.g:1691:2: ( ruleNode )
            // InternalPf.g:1692:3: ruleNode
            {
             before(grammarAccess.getProblemDiagramAccess().getNodesNodeParserRuleCall_3_0_0()); 
            pushFollow(FOLLOW_2);
            ruleNode();

            state._fsp--;

             after(grammarAccess.getProblemDiagramAccess().getNodesNodeParserRuleCall_3_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__NodesAssignment_3_0"


    // $ANTLR start "rule__ProblemDiagram__LinksAssignment_3_1"
    // InternalPf.g:1701:1: rule__ProblemDiagram__LinksAssignment_3_1 : ( ruleLink ) ;
    public final void rule__ProblemDiagram__LinksAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1705:1: ( ( ruleLink ) )
            // InternalPf.g:1706:2: ( ruleLink )
            {
            // InternalPf.g:1706:2: ( ruleLink )
            // InternalPf.g:1707:3: ruleLink
            {
             before(grammarAccess.getProblemDiagramAccess().getLinksLinkParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_2);
            ruleLink();

            state._fsp--;

             after(grammarAccess.getProblemDiagramAccess().getLinksLinkParserRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ProblemDiagram__LinksAssignment_3_1"


    // $ANTLR start "rule__Node__NameAssignment_0"
    // InternalPf.g:1716:1: rule__Node__NameAssignment_0 : ( RULE_ID ) ;
    public final void rule__Node__NameAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1720:1: ( ( RULE_ID ) )
            // InternalPf.g:1721:2: ( RULE_ID )
            {
            // InternalPf.g:1721:2: ( RULE_ID )
            // InternalPf.g:1722:3: RULE_ID
            {
             before(grammarAccess.getNodeAccess().getNameIDTerminalRuleCall_0_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getNodeAccess().getNameIDTerminalRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__NameAssignment_0"


    // $ANTLR start "rule__Node__TypeAssignment_1"
    // InternalPf.g:1731:1: rule__Node__TypeAssignment_1 : ( ruleNodeType ) ;
    public final void rule__Node__TypeAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1735:1: ( ( ruleNodeType ) )
            // InternalPf.g:1736:2: ( ruleNodeType )
            {
            // InternalPf.g:1736:2: ( ruleNodeType )
            // InternalPf.g:1737:3: ruleNodeType
            {
             before(grammarAccess.getNodeAccess().getTypeNodeTypeEnumRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleNodeType();

            state._fsp--;

             after(grammarAccess.getNodeAccess().getTypeNodeTypeEnumRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__TypeAssignment_1"


    // $ANTLR start "rule__Node__DescriptionAssignment_2_1"
    // InternalPf.g:1746:1: rule__Node__DescriptionAssignment_2_1 : ( RULE_STRING ) ;
    public final void rule__Node__DescriptionAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1750:1: ( ( RULE_STRING ) )
            // InternalPf.g:1751:2: ( RULE_STRING )
            {
            // InternalPf.g:1751:2: ( RULE_STRING )
            // InternalPf.g:1752:3: RULE_STRING
            {
             before(grammarAccess.getNodeAccess().getDescriptionSTRINGTerminalRuleCall_2_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getNodeAccess().getDescriptionSTRINGTerminalRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__DescriptionAssignment_2_1"


    // $ANTLR start "rule__Node__HiddenPhenomenaAssignment_3_1_0"
    // InternalPf.g:1761:1: rule__Node__HiddenPhenomenaAssignment_3_1_0 : ( rulePhenomenon ) ;
    public final void rule__Node__HiddenPhenomenaAssignment_3_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1765:1: ( ( rulePhenomenon ) )
            // InternalPf.g:1766:2: ( rulePhenomenon )
            {
            // InternalPf.g:1766:2: ( rulePhenomenon )
            // InternalPf.g:1767:3: rulePhenomenon
            {
             before(grammarAccess.getNodeAccess().getHiddenPhenomenaPhenomenonParserRuleCall_3_1_0_0()); 
            pushFollow(FOLLOW_2);
            rulePhenomenon();

            state._fsp--;

             after(grammarAccess.getNodeAccess().getHiddenPhenomenaPhenomenonParserRuleCall_3_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__HiddenPhenomenaAssignment_3_1_0"


    // $ANTLR start "rule__Node__HiddenPhenomenaAssignment_3_1_1_1"
    // InternalPf.g:1776:1: rule__Node__HiddenPhenomenaAssignment_3_1_1_1 : ( rulePhenomenon ) ;
    public final void rule__Node__HiddenPhenomenaAssignment_3_1_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1780:1: ( ( rulePhenomenon ) )
            // InternalPf.g:1781:2: ( rulePhenomenon )
            {
            // InternalPf.g:1781:2: ( rulePhenomenon )
            // InternalPf.g:1782:3: rulePhenomenon
            {
             before(grammarAccess.getNodeAccess().getHiddenPhenomenaPhenomenonParserRuleCall_3_1_1_1_0()); 
            pushFollow(FOLLOW_2);
            rulePhenomenon();

            state._fsp--;

             after(grammarAccess.getNodeAccess().getHiddenPhenomenaPhenomenonParserRuleCall_3_1_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__HiddenPhenomenaAssignment_3_1_1_1"


    // $ANTLR start "rule__Node__SubproblemAssignment_3_2_0"
    // InternalPf.g:1791:1: rule__Node__SubproblemAssignment_3_2_0 : ( ruleProblemDiagram ) ;
    public final void rule__Node__SubproblemAssignment_3_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1795:1: ( ( ruleProblemDiagram ) )
            // InternalPf.g:1796:2: ( ruleProblemDiagram )
            {
            // InternalPf.g:1796:2: ( ruleProblemDiagram )
            // InternalPf.g:1797:3: ruleProblemDiagram
            {
             before(grammarAccess.getNodeAccess().getSubproblemProblemDiagramParserRuleCall_3_2_0_0()); 
            pushFollow(FOLLOW_2);
            ruleProblemDiagram();

            state._fsp--;

             after(grammarAccess.getNodeAccess().getSubproblemProblemDiagramParserRuleCall_3_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__SubproblemAssignment_3_2_0"


    // $ANTLR start "rule__Node__ProblemNodeRefAssignment_3_2_1_2"
    // InternalPf.g:1806:1: rule__Node__ProblemNodeRefAssignment_3_2_1_2 : ( ( RULE_ID ) ) ;
    public final void rule__Node__ProblemNodeRefAssignment_3_2_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1810:1: ( ( ( RULE_ID ) ) )
            // InternalPf.g:1811:2: ( ( RULE_ID ) )
            {
            // InternalPf.g:1811:2: ( ( RULE_ID ) )
            // InternalPf.g:1812:3: ( RULE_ID )
            {
             before(grammarAccess.getNodeAccess().getProblemNodeRefNodeCrossReference_3_2_1_2_0()); 
            // InternalPf.g:1813:3: ( RULE_ID )
            // InternalPf.g:1814:4: RULE_ID
            {
             before(grammarAccess.getNodeAccess().getProblemNodeRefNodeIDTerminalRuleCall_3_2_1_2_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getNodeAccess().getProblemNodeRefNodeIDTerminalRuleCall_3_2_1_2_0_1()); 

            }

             after(grammarAccess.getNodeAccess().getProblemNodeRefNodeCrossReference_3_2_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__ProblemNodeRefAssignment_3_2_1_2"


    // $ANTLR start "rule__Node__ProblemRefAssignment_3_2_2_2"
    // InternalPf.g:1825:1: rule__Node__ProblemRefAssignment_3_2_2_2 : ( ( RULE_ID ) ) ;
    public final void rule__Node__ProblemRefAssignment_3_2_2_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1829:1: ( ( ( RULE_ID ) ) )
            // InternalPf.g:1830:2: ( ( RULE_ID ) )
            {
            // InternalPf.g:1830:2: ( ( RULE_ID ) )
            // InternalPf.g:1831:3: ( RULE_ID )
            {
             before(grammarAccess.getNodeAccess().getProblemRefProblemDiagramCrossReference_3_2_2_2_0()); 
            // InternalPf.g:1832:3: ( RULE_ID )
            // InternalPf.g:1833:4: RULE_ID
            {
             before(grammarAccess.getNodeAccess().getProblemRefProblemDiagramIDTerminalRuleCall_3_2_2_2_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getNodeAccess().getProblemRefProblemDiagramIDTerminalRuleCall_3_2_2_2_0_1()); 

            }

             after(grammarAccess.getNodeAccess().getProblemRefProblemDiagramCrossReference_3_2_2_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__ProblemRefAssignment_3_2_2_2"


    // $ANTLR start "rule__Node__HrefAssignment_3_2_3_1"
    // InternalPf.g:1844:1: rule__Node__HrefAssignment_3_2_3_1 : ( RULE_STRING ) ;
    public final void rule__Node__HrefAssignment_3_2_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1848:1: ( ( RULE_STRING ) )
            // InternalPf.g:1849:2: ( RULE_STRING )
            {
            // InternalPf.g:1849:2: ( RULE_STRING )
            // InternalPf.g:1850:3: RULE_STRING
            {
             before(grammarAccess.getNodeAccess().getHrefSTRINGTerminalRuleCall_3_2_3_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getNodeAccess().getHrefSTRINGTerminalRuleCall_3_2_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Node__HrefAssignment_3_2_3_1"


    // $ANTLR start "rule__Phenomenon__TypeAssignment_0"
    // InternalPf.g:1859:1: rule__Phenomenon__TypeAssignment_0 : ( rulePhenomenonType ) ;
    public final void rule__Phenomenon__TypeAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1863:1: ( ( rulePhenomenonType ) )
            // InternalPf.g:1864:2: ( rulePhenomenonType )
            {
            // InternalPf.g:1864:2: ( rulePhenomenonType )
            // InternalPf.g:1865:3: rulePhenomenonType
            {
             before(grammarAccess.getPhenomenonAccess().getTypePhenomenonTypeEnumRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            rulePhenomenonType();

            state._fsp--;

             after(grammarAccess.getPhenomenonAccess().getTypePhenomenonTypeEnumRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__TypeAssignment_0"


    // $ANTLR start "rule__Phenomenon__IsControlledAssignment_1"
    // InternalPf.g:1874:1: rule__Phenomenon__IsControlledAssignment_1 : ( ( '!' ) ) ;
    public final void rule__Phenomenon__IsControlledAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1878:1: ( ( ( '!' ) ) )
            // InternalPf.g:1879:2: ( ( '!' ) )
            {
            // InternalPf.g:1879:2: ( ( '!' ) )
            // InternalPf.g:1880:3: ( '!' )
            {
             before(grammarAccess.getPhenomenonAccess().getIsControlledExclamationMarkKeyword_1_0()); 
            // InternalPf.g:1881:3: ( '!' )
            // InternalPf.g:1882:4: '!'
            {
             before(grammarAccess.getPhenomenonAccess().getIsControlledExclamationMarkKeyword_1_0()); 
            match(input,38,FOLLOW_2); 
             after(grammarAccess.getPhenomenonAccess().getIsControlledExclamationMarkKeyword_1_0()); 

            }

             after(grammarAccess.getPhenomenonAccess().getIsControlledExclamationMarkKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__IsControlledAssignment_1"


    // $ANTLR start "rule__Phenomenon__NameAssignment_2"
    // InternalPf.g:1893:1: rule__Phenomenon__NameAssignment_2 : ( RULE_ID ) ;
    public final void rule__Phenomenon__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1897:1: ( ( RULE_ID ) )
            // InternalPf.g:1898:2: ( RULE_ID )
            {
            // InternalPf.g:1898:2: ( RULE_ID )
            // InternalPf.g:1899:3: RULE_ID
            {
             before(grammarAccess.getPhenomenonAccess().getNameIDTerminalRuleCall_2_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getPhenomenonAccess().getNameIDTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__NameAssignment_2"


    // $ANTLR start "rule__Phenomenon__DescriptionAssignment_3_1"
    // InternalPf.g:1908:1: rule__Phenomenon__DescriptionAssignment_3_1 : ( RULE_STRING ) ;
    public final void rule__Phenomenon__DescriptionAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1912:1: ( ( RULE_STRING ) )
            // InternalPf.g:1913:2: ( RULE_STRING )
            {
            // InternalPf.g:1913:2: ( RULE_STRING )
            // InternalPf.g:1914:3: RULE_STRING
            {
             before(grammarAccess.getPhenomenonAccess().getDescriptionSTRINGTerminalRuleCall_3_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getPhenomenonAccess().getDescriptionSTRINGTerminalRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Phenomenon__DescriptionAssignment_3_1"


    // $ANTLR start "rule__Link__FromAssignment_0"
    // InternalPf.g:1923:1: rule__Link__FromAssignment_0 : ( ( RULE_ID ) ) ;
    public final void rule__Link__FromAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1927:1: ( ( ( RULE_ID ) ) )
            // InternalPf.g:1928:2: ( ( RULE_ID ) )
            {
            // InternalPf.g:1928:2: ( ( RULE_ID ) )
            // InternalPf.g:1929:3: ( RULE_ID )
            {
             before(grammarAccess.getLinkAccess().getFromNodeCrossReference_0_0()); 
            // InternalPf.g:1930:3: ( RULE_ID )
            // InternalPf.g:1931:4: RULE_ID
            {
             before(grammarAccess.getLinkAccess().getFromNodeIDTerminalRuleCall_0_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getLinkAccess().getFromNodeIDTerminalRuleCall_0_0_1()); 

            }

             after(grammarAccess.getLinkAccess().getFromNodeCrossReference_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__FromAssignment_0"


    // $ANTLR start "rule__Link__TypeAssignment_1"
    // InternalPf.g:1942:1: rule__Link__TypeAssignment_1 : ( ruleLinkType ) ;
    public final void rule__Link__TypeAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1946:1: ( ( ruleLinkType ) )
            // InternalPf.g:1947:2: ( ruleLinkType )
            {
            // InternalPf.g:1947:2: ( ruleLinkType )
            // InternalPf.g:1948:3: ruleLinkType
            {
             before(grammarAccess.getLinkAccess().getTypeLinkTypeEnumRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleLinkType();

            state._fsp--;

             after(grammarAccess.getLinkAccess().getTypeLinkTypeEnumRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__TypeAssignment_1"


    // $ANTLR start "rule__Link__ToAssignment_2"
    // InternalPf.g:1957:1: rule__Link__ToAssignment_2 : ( ( RULE_ID ) ) ;
    public final void rule__Link__ToAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1961:1: ( ( ( RULE_ID ) ) )
            // InternalPf.g:1962:2: ( ( RULE_ID ) )
            {
            // InternalPf.g:1962:2: ( ( RULE_ID ) )
            // InternalPf.g:1963:3: ( RULE_ID )
            {
             before(grammarAccess.getLinkAccess().getToNodeCrossReference_2_0()); 
            // InternalPf.g:1964:3: ( RULE_ID )
            // InternalPf.g:1965:4: RULE_ID
            {
             before(grammarAccess.getLinkAccess().getToNodeIDTerminalRuleCall_2_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getLinkAccess().getToNodeIDTerminalRuleCall_2_0_1()); 

            }

             after(grammarAccess.getLinkAccess().getToNodeCrossReference_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__ToAssignment_2"


    // $ANTLR start "rule__Link__PhenomenaAssignment_3_1"
    // InternalPf.g:1976:1: rule__Link__PhenomenaAssignment_3_1 : ( rulePhenomenon ) ;
    public final void rule__Link__PhenomenaAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1980:1: ( ( rulePhenomenon ) )
            // InternalPf.g:1981:2: ( rulePhenomenon )
            {
            // InternalPf.g:1981:2: ( rulePhenomenon )
            // InternalPf.g:1982:3: rulePhenomenon
            {
             before(grammarAccess.getLinkAccess().getPhenomenaPhenomenonParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_2);
            rulePhenomenon();

            state._fsp--;

             after(grammarAccess.getLinkAccess().getPhenomenaPhenomenonParserRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__PhenomenaAssignment_3_1"


    // $ANTLR start "rule__Link__PhenomenaAssignment_3_2_1"
    // InternalPf.g:1991:1: rule__Link__PhenomenaAssignment_3_2_1 : ( rulePhenomenon ) ;
    public final void rule__Link__PhenomenaAssignment_3_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:1995:1: ( ( rulePhenomenon ) )
            // InternalPf.g:1996:2: ( rulePhenomenon )
            {
            // InternalPf.g:1996:2: ( rulePhenomenon )
            // InternalPf.g:1997:3: rulePhenomenon
            {
             before(grammarAccess.getLinkAccess().getPhenomenaPhenomenonParserRuleCall_3_2_1_0()); 
            pushFollow(FOLLOW_2);
            rulePhenomenon();

            state._fsp--;

             after(grammarAccess.getLinkAccess().getPhenomenaPhenomenonParserRuleCall_3_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__PhenomenaAssignment_3_2_1"


    // $ANTLR start "rule__Link__DescriptionAssignment_4_1"
    // InternalPf.g:2006:1: rule__Link__DescriptionAssignment_4_1 : ( RULE_STRING ) ;
    public final void rule__Link__DescriptionAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalPf.g:2010:1: ( ( RULE_STRING ) )
            // InternalPf.g:2011:2: ( RULE_STRING )
            {
            // InternalPf.g:2011:2: ( RULE_STRING )
            // InternalPf.g:2012:3: RULE_STRING
            {
             before(grammarAccess.getLinkAccess().getDescriptionSTRINGTerminalRuleCall_4_1_0()); 
            match(input,RULE_STRING,FOLLOW_2); 
             after(grammarAccess.getLinkAccess().getDescriptionSTRINGTerminalRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Link__DescriptionAssignment_4_1"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000040000010L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x00000000000FF000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000180000020L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000080000020L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000004A20F00010L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000820000002L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000004000F00010L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x000000001F000000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000600000000L});

}