package pf.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import pf.services.PfGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalPfParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_QUALIFIED_NAME", "RULE_INT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'problem:'", "'for'", "':'", "'{'", "','", "'see'", "'domain'", "'problem'", "'}'", "'!'", "'R'", "'M'", "'B'", "'X'", "'C'", "'D'", "'P'", "'?'", "'phenomenon'", "'event'", "'state'", "'value'", "'--'", "'~~'", "'<~'", "'->'", "'~>'"
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

        public InternalPfParser(TokenStream input, PfGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "ProblemDiagram";
       	}

       	@Override
       	protected PfGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleProblemDiagram"
    // InternalPf.g:65:1: entryRuleProblemDiagram returns [EObject current=null] : iv_ruleProblemDiagram= ruleProblemDiagram EOF ;
    public final EObject entryRuleProblemDiagram() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProblemDiagram = null;


        try {
            // InternalPf.g:65:55: (iv_ruleProblemDiagram= ruleProblemDiagram EOF )
            // InternalPf.g:66:2: iv_ruleProblemDiagram= ruleProblemDiagram EOF
            {
             newCompositeNode(grammarAccess.getProblemDiagramRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleProblemDiagram=ruleProblemDiagram();

            state._fsp--;

             current =iv_ruleProblemDiagram; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleProblemDiagram"


    // $ANTLR start "ruleProblemDiagram"
    // InternalPf.g:72:1: ruleProblemDiagram returns [EObject current=null] : (otherlv_0= 'problem:' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'for' ( (otherlv_3= RULE_ID ) ) )? ( ( (lv_nodes_4_0= ruleNode ) ) | ( (lv_links_5_0= ruleLink ) ) )* ) ;
    public final EObject ruleProblemDiagram() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        EObject lv_nodes_4_0 = null;

        EObject lv_links_5_0 = null;



        	enterRule();

        try {
            // InternalPf.g:78:2: ( (otherlv_0= 'problem:' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'for' ( (otherlv_3= RULE_ID ) ) )? ( ( (lv_nodes_4_0= ruleNode ) ) | ( (lv_links_5_0= ruleLink ) ) )* ) )
            // InternalPf.g:79:2: (otherlv_0= 'problem:' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'for' ( (otherlv_3= RULE_ID ) ) )? ( ( (lv_nodes_4_0= ruleNode ) ) | ( (lv_links_5_0= ruleLink ) ) )* )
            {
            // InternalPf.g:79:2: (otherlv_0= 'problem:' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'for' ( (otherlv_3= RULE_ID ) ) )? ( ( (lv_nodes_4_0= ruleNode ) ) | ( (lv_links_5_0= ruleLink ) ) )* )
            // InternalPf.g:80:3: otherlv_0= 'problem:' ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= 'for' ( (otherlv_3= RULE_ID ) ) )? ( ( (lv_nodes_4_0= ruleNode ) ) | ( (lv_links_5_0= ruleLink ) ) )*
            {
            otherlv_0=(Token)match(input,12,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getProblemDiagramAccess().getProblemKeyword_0());
            		
            // InternalPf.g:84:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalPf.g:85:4: (lv_name_1_0= RULE_ID )
            {
            // InternalPf.g:85:4: (lv_name_1_0= RULE_ID )
            // InternalPf.g:86:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getProblemDiagramAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProblemDiagramRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"pf.Pf.ID");
            				

            }


            }

            // InternalPf.g:102:3: (otherlv_2= 'for' ( (otherlv_3= RULE_ID ) ) )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==13) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // InternalPf.g:103:4: otherlv_2= 'for' ( (otherlv_3= RULE_ID ) )
                    {
                    otherlv_2=(Token)match(input,13,FOLLOW_3); 

                    				newLeafNode(otherlv_2, grammarAccess.getProblemDiagramAccess().getForKeyword_2_0());
                    			
                    // InternalPf.g:107:4: ( (otherlv_3= RULE_ID ) )
                    // InternalPf.g:108:5: (otherlv_3= RULE_ID )
                    {
                    // InternalPf.g:108:5: (otherlv_3= RULE_ID )
                    // InternalPf.g:109:6: otherlv_3= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getProblemDiagramRule());
                    						}
                    					
                    otherlv_3=(Token)match(input,RULE_ID,FOLLOW_5); 

                    						newLeafNode(otherlv_3, grammarAccess.getProblemDiagramAccess().getHighlightNodeCrossReference_2_1_0());
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalPf.g:121:3: ( ( (lv_nodes_4_0= ruleNode ) ) | ( (lv_links_5_0= ruleLink ) ) )*
            loop2:
            do {
                int alt2=3;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==RULE_ID) ) {
                    int LA2_2 = input.LA(2);

                    if ( ((LA2_2>=34 && LA2_2<=38)) ) {
                        alt2=2;
                    }
                    else if ( ((LA2_2>=22 && LA2_2<=29)) ) {
                        alt2=1;
                    }


                }


                switch (alt2) {
            	case 1 :
            	    // InternalPf.g:122:4: ( (lv_nodes_4_0= ruleNode ) )
            	    {
            	    // InternalPf.g:122:4: ( (lv_nodes_4_0= ruleNode ) )
            	    // InternalPf.g:123:5: (lv_nodes_4_0= ruleNode )
            	    {
            	    // InternalPf.g:123:5: (lv_nodes_4_0= ruleNode )
            	    // InternalPf.g:124:6: lv_nodes_4_0= ruleNode
            	    {

            	    						newCompositeNode(grammarAccess.getProblemDiagramAccess().getNodesNodeParserRuleCall_3_0_0());
            	    					
            	    pushFollow(FOLLOW_5);
            	    lv_nodes_4_0=ruleNode();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getProblemDiagramRule());
            	    						}
            	    						add(
            	    							current,
            	    							"nodes",
            	    							lv_nodes_4_0,
            	    							"pf.Pf.Node");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;
            	case 2 :
            	    // InternalPf.g:142:4: ( (lv_links_5_0= ruleLink ) )
            	    {
            	    // InternalPf.g:142:4: ( (lv_links_5_0= ruleLink ) )
            	    // InternalPf.g:143:5: (lv_links_5_0= ruleLink )
            	    {
            	    // InternalPf.g:143:5: (lv_links_5_0= ruleLink )
            	    // InternalPf.g:144:6: lv_links_5_0= ruleLink
            	    {

            	    						newCompositeNode(grammarAccess.getProblemDiagramAccess().getLinksLinkParserRuleCall_3_1_0());
            	    					
            	    pushFollow(FOLLOW_5);
            	    lv_links_5_0=ruleLink();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getProblemDiagramRule());
            	    						}
            	    						add(
            	    							current,
            	    							"links",
            	    							lv_links_5_0,
            	    							"pf.Pf.Link");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProblemDiagram"


    // $ANTLR start "entryRuleNode"
    // InternalPf.g:166:1: entryRuleNode returns [EObject current=null] : iv_ruleNode= ruleNode EOF ;
    public final EObject entryRuleNode() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNode = null;


        try {
            // InternalPf.g:166:45: (iv_ruleNode= ruleNode EOF )
            // InternalPf.g:167:2: iv_ruleNode= ruleNode EOF
            {
             newCompositeNode(grammarAccess.getNodeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNode=ruleNode();

            state._fsp--;

             current =iv_ruleNode; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNode"


    // $ANTLR start "ruleNode"
    // InternalPf.g:173:1: ruleNode returns [EObject current=null] : ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_type_1_0= ruleNodeType ) ) ( (otherlv_2= ':' )? ( (lv_description_3_0= RULE_STRING ) ) )? (otherlv_4= '{' ( ( (lv_hiddenPhenomena_5_0= rulePhenomenon ) ) (otherlv_6= ',' ( (lv_hiddenPhenomena_7_0= rulePhenomenon ) ) )* )? ( ( (lv_subproblem_8_0= ruleProblemDiagram ) ) | (otherlv_9= 'see' otherlv_10= 'domain' ( (otherlv_11= RULE_ID ) ) ) | (otherlv_12= 'see' otherlv_13= 'problem' ( (otherlv_14= RULE_ID ) ) ) | (otherlv_15= 'see' ( (lv_href_16_0= RULE_STRING ) ) ) )* otherlv_17= '}' )? ) ;
    public final EObject ruleNode() throws RecognitionException {
        EObject current = null;

        Token lv_name_0_0=null;
        Token otherlv_2=null;
        Token lv_description_3_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token lv_href_16_0=null;
        Token otherlv_17=null;
        Enumerator lv_type_1_0 = null;

        EObject lv_hiddenPhenomena_5_0 = null;

        EObject lv_hiddenPhenomena_7_0 = null;

        EObject lv_subproblem_8_0 = null;



        	enterRule();

        try {
            // InternalPf.g:179:2: ( ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_type_1_0= ruleNodeType ) ) ( (otherlv_2= ':' )? ( (lv_description_3_0= RULE_STRING ) ) )? (otherlv_4= '{' ( ( (lv_hiddenPhenomena_5_0= rulePhenomenon ) ) (otherlv_6= ',' ( (lv_hiddenPhenomena_7_0= rulePhenomenon ) ) )* )? ( ( (lv_subproblem_8_0= ruleProblemDiagram ) ) | (otherlv_9= 'see' otherlv_10= 'domain' ( (otherlv_11= RULE_ID ) ) ) | (otherlv_12= 'see' otherlv_13= 'problem' ( (otherlv_14= RULE_ID ) ) ) | (otherlv_15= 'see' ( (lv_href_16_0= RULE_STRING ) ) ) )* otherlv_17= '}' )? ) )
            // InternalPf.g:180:2: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_type_1_0= ruleNodeType ) ) ( (otherlv_2= ':' )? ( (lv_description_3_0= RULE_STRING ) ) )? (otherlv_4= '{' ( ( (lv_hiddenPhenomena_5_0= rulePhenomenon ) ) (otherlv_6= ',' ( (lv_hiddenPhenomena_7_0= rulePhenomenon ) ) )* )? ( ( (lv_subproblem_8_0= ruleProblemDiagram ) ) | (otherlv_9= 'see' otherlv_10= 'domain' ( (otherlv_11= RULE_ID ) ) ) | (otherlv_12= 'see' otherlv_13= 'problem' ( (otherlv_14= RULE_ID ) ) ) | (otherlv_15= 'see' ( (lv_href_16_0= RULE_STRING ) ) ) )* otherlv_17= '}' )? )
            {
            // InternalPf.g:180:2: ( ( (lv_name_0_0= RULE_ID ) ) ( (lv_type_1_0= ruleNodeType ) ) ( (otherlv_2= ':' )? ( (lv_description_3_0= RULE_STRING ) ) )? (otherlv_4= '{' ( ( (lv_hiddenPhenomena_5_0= rulePhenomenon ) ) (otherlv_6= ',' ( (lv_hiddenPhenomena_7_0= rulePhenomenon ) ) )* )? ( ( (lv_subproblem_8_0= ruleProblemDiagram ) ) | (otherlv_9= 'see' otherlv_10= 'domain' ( (otherlv_11= RULE_ID ) ) ) | (otherlv_12= 'see' otherlv_13= 'problem' ( (otherlv_14= RULE_ID ) ) ) | (otherlv_15= 'see' ( (lv_href_16_0= RULE_STRING ) ) ) )* otherlv_17= '}' )? )
            // InternalPf.g:181:3: ( (lv_name_0_0= RULE_ID ) ) ( (lv_type_1_0= ruleNodeType ) ) ( (otherlv_2= ':' )? ( (lv_description_3_0= RULE_STRING ) ) )? (otherlv_4= '{' ( ( (lv_hiddenPhenomena_5_0= rulePhenomenon ) ) (otherlv_6= ',' ( (lv_hiddenPhenomena_7_0= rulePhenomenon ) ) )* )? ( ( (lv_subproblem_8_0= ruleProblemDiagram ) ) | (otherlv_9= 'see' otherlv_10= 'domain' ( (otherlv_11= RULE_ID ) ) ) | (otherlv_12= 'see' otherlv_13= 'problem' ( (otherlv_14= RULE_ID ) ) ) | (otherlv_15= 'see' ( (lv_href_16_0= RULE_STRING ) ) ) )* otherlv_17= '}' )?
            {
            // InternalPf.g:181:3: ( (lv_name_0_0= RULE_ID ) )
            // InternalPf.g:182:4: (lv_name_0_0= RULE_ID )
            {
            // InternalPf.g:182:4: (lv_name_0_0= RULE_ID )
            // InternalPf.g:183:5: lv_name_0_0= RULE_ID
            {
            lv_name_0_0=(Token)match(input,RULE_ID,FOLLOW_6); 

            					newLeafNode(lv_name_0_0, grammarAccess.getNodeAccess().getNameIDTerminalRuleCall_0_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getNodeRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_0_0,
            						"pf.Pf.ID");
            				

            }


            }

            // InternalPf.g:199:3: ( (lv_type_1_0= ruleNodeType ) )
            // InternalPf.g:200:4: (lv_type_1_0= ruleNodeType )
            {
            // InternalPf.g:200:4: (lv_type_1_0= ruleNodeType )
            // InternalPf.g:201:5: lv_type_1_0= ruleNodeType
            {

            					newCompositeNode(grammarAccess.getNodeAccess().getTypeNodeTypeEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_7);
            lv_type_1_0=ruleNodeType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getNodeRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_1_0,
            						"pf.Pf.NodeType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalPf.g:218:3: ( (otherlv_2= ':' )? ( (lv_description_3_0= RULE_STRING ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==RULE_STRING||LA4_0==14) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalPf.g:219:4: (otherlv_2= ':' )? ( (lv_description_3_0= RULE_STRING ) )
                    {
                    // InternalPf.g:219:4: (otherlv_2= ':' )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==14) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // InternalPf.g:220:5: otherlv_2= ':'
                            {
                            otherlv_2=(Token)match(input,14,FOLLOW_8); 

                            					newLeafNode(otherlv_2, grammarAccess.getNodeAccess().getColonKeyword_2_0());
                            				

                            }
                            break;

                    }

                    // InternalPf.g:225:4: ( (lv_description_3_0= RULE_STRING ) )
                    // InternalPf.g:226:5: (lv_description_3_0= RULE_STRING )
                    {
                    // InternalPf.g:226:5: (lv_description_3_0= RULE_STRING )
                    // InternalPf.g:227:6: lv_description_3_0= RULE_STRING
                    {
                    lv_description_3_0=(Token)match(input,RULE_STRING,FOLLOW_9); 

                    						newLeafNode(lv_description_3_0, grammarAccess.getNodeAccess().getDescriptionSTRINGTerminalRuleCall_2_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getNodeRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"description",
                    							lv_description_3_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalPf.g:244:3: (otherlv_4= '{' ( ( (lv_hiddenPhenomena_5_0= rulePhenomenon ) ) (otherlv_6= ',' ( (lv_hiddenPhenomena_7_0= rulePhenomenon ) ) )* )? ( ( (lv_subproblem_8_0= ruleProblemDiagram ) ) | (otherlv_9= 'see' otherlv_10= 'domain' ( (otherlv_11= RULE_ID ) ) ) | (otherlv_12= 'see' otherlv_13= 'problem' ( (otherlv_14= RULE_ID ) ) ) | (otherlv_15= 'see' ( (lv_href_16_0= RULE_STRING ) ) ) )* otherlv_17= '}' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==15) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalPf.g:245:4: otherlv_4= '{' ( ( (lv_hiddenPhenomena_5_0= rulePhenomenon ) ) (otherlv_6= ',' ( (lv_hiddenPhenomena_7_0= rulePhenomenon ) ) )* )? ( ( (lv_subproblem_8_0= ruleProblemDiagram ) ) | (otherlv_9= 'see' otherlv_10= 'domain' ( (otherlv_11= RULE_ID ) ) ) | (otherlv_12= 'see' otherlv_13= 'problem' ( (otherlv_14= RULE_ID ) ) ) | (otherlv_15= 'see' ( (lv_href_16_0= RULE_STRING ) ) ) )* otherlv_17= '}'
                    {
                    otherlv_4=(Token)match(input,15,FOLLOW_10); 

                    				newLeafNode(otherlv_4, grammarAccess.getNodeAccess().getLeftCurlyBracketKeyword_3_0());
                    			
                    // InternalPf.g:249:4: ( ( (lv_hiddenPhenomena_5_0= rulePhenomenon ) ) (otherlv_6= ',' ( (lv_hiddenPhenomena_7_0= rulePhenomenon ) ) )* )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0==RULE_ID||LA6_0==21||(LA6_0>=30 && LA6_0<=33)) ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // InternalPf.g:250:5: ( (lv_hiddenPhenomena_5_0= rulePhenomenon ) ) (otherlv_6= ',' ( (lv_hiddenPhenomena_7_0= rulePhenomenon ) ) )*
                            {
                            // InternalPf.g:250:5: ( (lv_hiddenPhenomena_5_0= rulePhenomenon ) )
                            // InternalPf.g:251:6: (lv_hiddenPhenomena_5_0= rulePhenomenon )
                            {
                            // InternalPf.g:251:6: (lv_hiddenPhenomena_5_0= rulePhenomenon )
                            // InternalPf.g:252:7: lv_hiddenPhenomena_5_0= rulePhenomenon
                            {

                            							newCompositeNode(grammarAccess.getNodeAccess().getHiddenPhenomenaPhenomenonParserRuleCall_3_1_0_0());
                            						
                            pushFollow(FOLLOW_11);
                            lv_hiddenPhenomena_5_0=rulePhenomenon();

                            state._fsp--;


                            							if (current==null) {
                            								current = createModelElementForParent(grammarAccess.getNodeRule());
                            							}
                            							add(
                            								current,
                            								"hiddenPhenomena",
                            								lv_hiddenPhenomena_5_0,
                            								"pf.Pf.Phenomenon");
                            							afterParserOrEnumRuleCall();
                            						

                            }


                            }

                            // InternalPf.g:269:5: (otherlv_6= ',' ( (lv_hiddenPhenomena_7_0= rulePhenomenon ) ) )*
                            loop5:
                            do {
                                int alt5=2;
                                int LA5_0 = input.LA(1);

                                if ( (LA5_0==16) ) {
                                    alt5=1;
                                }


                                switch (alt5) {
                            	case 1 :
                            	    // InternalPf.g:270:6: otherlv_6= ',' ( (lv_hiddenPhenomena_7_0= rulePhenomenon ) )
                            	    {
                            	    otherlv_6=(Token)match(input,16,FOLLOW_12); 

                            	    						newLeafNode(otherlv_6, grammarAccess.getNodeAccess().getCommaKeyword_3_1_1_0());
                            	    					
                            	    // InternalPf.g:274:6: ( (lv_hiddenPhenomena_7_0= rulePhenomenon ) )
                            	    // InternalPf.g:275:7: (lv_hiddenPhenomena_7_0= rulePhenomenon )
                            	    {
                            	    // InternalPf.g:275:7: (lv_hiddenPhenomena_7_0= rulePhenomenon )
                            	    // InternalPf.g:276:8: lv_hiddenPhenomena_7_0= rulePhenomenon
                            	    {

                            	    								newCompositeNode(grammarAccess.getNodeAccess().getHiddenPhenomenaPhenomenonParserRuleCall_3_1_1_1_0());
                            	    							
                            	    pushFollow(FOLLOW_11);
                            	    lv_hiddenPhenomena_7_0=rulePhenomenon();

                            	    state._fsp--;


                            	    								if (current==null) {
                            	    									current = createModelElementForParent(grammarAccess.getNodeRule());
                            	    								}
                            	    								add(
                            	    									current,
                            	    									"hiddenPhenomena",
                            	    									lv_hiddenPhenomena_7_0,
                            	    									"pf.Pf.Phenomenon");
                            	    								afterParserOrEnumRuleCall();
                            	    							

                            	    }


                            	    }


                            	    }
                            	    break;

                            	default :
                            	    break loop5;
                                }
                            } while (true);


                            }
                            break;

                    }

                    // InternalPf.g:295:4: ( ( (lv_subproblem_8_0= ruleProblemDiagram ) ) | (otherlv_9= 'see' otherlv_10= 'domain' ( (otherlv_11= RULE_ID ) ) ) | (otherlv_12= 'see' otherlv_13= 'problem' ( (otherlv_14= RULE_ID ) ) ) | (otherlv_15= 'see' ( (lv_href_16_0= RULE_STRING ) ) ) )*
                    loop7:
                    do {
                        int alt7=5;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==12) ) {
                            alt7=1;
                        }
                        else if ( (LA7_0==17) ) {
                            switch ( input.LA(2) ) {
                            case 18:
                                {
                                alt7=2;
                                }
                                break;
                            case 19:
                                {
                                alt7=3;
                                }
                                break;
                            case RULE_STRING:
                                {
                                alt7=4;
                                }
                                break;

                            }

                        }


                        switch (alt7) {
                    	case 1 :
                    	    // InternalPf.g:296:5: ( (lv_subproblem_8_0= ruleProblemDiagram ) )
                    	    {
                    	    // InternalPf.g:296:5: ( (lv_subproblem_8_0= ruleProblemDiagram ) )
                    	    // InternalPf.g:297:6: (lv_subproblem_8_0= ruleProblemDiagram )
                    	    {
                    	    // InternalPf.g:297:6: (lv_subproblem_8_0= ruleProblemDiagram )
                    	    // InternalPf.g:298:7: lv_subproblem_8_0= ruleProblemDiagram
                    	    {

                    	    							newCompositeNode(grammarAccess.getNodeAccess().getSubproblemProblemDiagramParserRuleCall_3_2_0_0());
                    	    						
                    	    pushFollow(FOLLOW_13);
                    	    lv_subproblem_8_0=ruleProblemDiagram();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getNodeRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"subproblem",
                    	    								lv_subproblem_8_0,
                    	    								"pf.Pf.ProblemDiagram");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 2 :
                    	    // InternalPf.g:316:5: (otherlv_9= 'see' otherlv_10= 'domain' ( (otherlv_11= RULE_ID ) ) )
                    	    {
                    	    // InternalPf.g:316:5: (otherlv_9= 'see' otherlv_10= 'domain' ( (otherlv_11= RULE_ID ) ) )
                    	    // InternalPf.g:317:6: otherlv_9= 'see' otherlv_10= 'domain' ( (otherlv_11= RULE_ID ) )
                    	    {
                    	    otherlv_9=(Token)match(input,17,FOLLOW_14); 

                    	    						newLeafNode(otherlv_9, grammarAccess.getNodeAccess().getSeeKeyword_3_2_1_0());
                    	    					
                    	    otherlv_10=(Token)match(input,18,FOLLOW_3); 

                    	    						newLeafNode(otherlv_10, grammarAccess.getNodeAccess().getDomainKeyword_3_2_1_1());
                    	    					
                    	    // InternalPf.g:325:6: ( (otherlv_11= RULE_ID ) )
                    	    // InternalPf.g:326:7: (otherlv_11= RULE_ID )
                    	    {
                    	    // InternalPf.g:326:7: (otherlv_11= RULE_ID )
                    	    // InternalPf.g:327:8: otherlv_11= RULE_ID
                    	    {

                    	    								if (current==null) {
                    	    									current = createModelElement(grammarAccess.getNodeRule());
                    	    								}
                    	    							
                    	    otherlv_11=(Token)match(input,RULE_ID,FOLLOW_13); 

                    	    								newLeafNode(otherlv_11, grammarAccess.getNodeAccess().getProblemNodeRefNodeCrossReference_3_2_1_2_0());
                    	    							

                    	    }


                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 3 :
                    	    // InternalPf.g:340:5: (otherlv_12= 'see' otherlv_13= 'problem' ( (otherlv_14= RULE_ID ) ) )
                    	    {
                    	    // InternalPf.g:340:5: (otherlv_12= 'see' otherlv_13= 'problem' ( (otherlv_14= RULE_ID ) ) )
                    	    // InternalPf.g:341:6: otherlv_12= 'see' otherlv_13= 'problem' ( (otherlv_14= RULE_ID ) )
                    	    {
                    	    otherlv_12=(Token)match(input,17,FOLLOW_15); 

                    	    						newLeafNode(otherlv_12, grammarAccess.getNodeAccess().getSeeKeyword_3_2_2_0());
                    	    					
                    	    otherlv_13=(Token)match(input,19,FOLLOW_3); 

                    	    						newLeafNode(otherlv_13, grammarAccess.getNodeAccess().getProblemKeyword_3_2_2_1());
                    	    					
                    	    // InternalPf.g:349:6: ( (otherlv_14= RULE_ID ) )
                    	    // InternalPf.g:350:7: (otherlv_14= RULE_ID )
                    	    {
                    	    // InternalPf.g:350:7: (otherlv_14= RULE_ID )
                    	    // InternalPf.g:351:8: otherlv_14= RULE_ID
                    	    {

                    	    								if (current==null) {
                    	    									current = createModelElement(grammarAccess.getNodeRule());
                    	    								}
                    	    							
                    	    otherlv_14=(Token)match(input,RULE_ID,FOLLOW_13); 

                    	    								newLeafNode(otherlv_14, grammarAccess.getNodeAccess().getProblemRefProblemDiagramCrossReference_3_2_2_2_0());
                    	    							

                    	    }


                    	    }


                    	    }


                    	    }
                    	    break;
                    	case 4 :
                    	    // InternalPf.g:364:5: (otherlv_15= 'see' ( (lv_href_16_0= RULE_STRING ) ) )
                    	    {
                    	    // InternalPf.g:364:5: (otherlv_15= 'see' ( (lv_href_16_0= RULE_STRING ) ) )
                    	    // InternalPf.g:365:6: otherlv_15= 'see' ( (lv_href_16_0= RULE_STRING ) )
                    	    {
                    	    otherlv_15=(Token)match(input,17,FOLLOW_8); 

                    	    						newLeafNode(otherlv_15, grammarAccess.getNodeAccess().getSeeKeyword_3_2_3_0());
                    	    					
                    	    // InternalPf.g:369:6: ( (lv_href_16_0= RULE_STRING ) )
                    	    // InternalPf.g:370:7: (lv_href_16_0= RULE_STRING )
                    	    {
                    	    // InternalPf.g:370:7: (lv_href_16_0= RULE_STRING )
                    	    // InternalPf.g:371:8: lv_href_16_0= RULE_STRING
                    	    {
                    	    lv_href_16_0=(Token)match(input,RULE_STRING,FOLLOW_13); 

                    	    								newLeafNode(lv_href_16_0, grammarAccess.getNodeAccess().getHrefSTRINGTerminalRuleCall_3_2_3_1_0());
                    	    							

                    	    								if (current==null) {
                    	    									current = createModelElement(grammarAccess.getNodeRule());
                    	    								}
                    	    								addWithLastConsumed(
                    	    									current,
                    	    									"href",
                    	    									lv_href_16_0,
                    	    									"org.eclipse.xtext.common.Terminals.STRING");
                    	    							

                    	    }


                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    otherlv_17=(Token)match(input,20,FOLLOW_2); 

                    				newLeafNode(otherlv_17, grammarAccess.getNodeAccess().getRightCurlyBracketKeyword_3_3());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNode"


    // $ANTLR start "entryRulePhenomenon"
    // InternalPf.g:398:1: entryRulePhenomenon returns [EObject current=null] : iv_rulePhenomenon= rulePhenomenon EOF ;
    public final EObject entryRulePhenomenon() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePhenomenon = null;


        try {
            // InternalPf.g:398:51: (iv_rulePhenomenon= rulePhenomenon EOF )
            // InternalPf.g:399:2: iv_rulePhenomenon= rulePhenomenon EOF
            {
             newCompositeNode(grammarAccess.getPhenomenonRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePhenomenon=rulePhenomenon();

            state._fsp--;

             current =iv_rulePhenomenon; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePhenomenon"


    // $ANTLR start "rulePhenomenon"
    // InternalPf.g:405:1: rulePhenomenon returns [EObject current=null] : ( ( (lv_type_0_0= rulePhenomenonType ) )? ( (lv_isControlled_1_0= '!' ) )? ( (lv_name_2_0= RULE_ID ) ) ( (otherlv_3= ':' )? ( (lv_description_4_0= RULE_STRING ) ) )? ) ;
    public final EObject rulePhenomenon() throws RecognitionException {
        EObject current = null;

        Token lv_isControlled_1_0=null;
        Token lv_name_2_0=null;
        Token otherlv_3=null;
        Token lv_description_4_0=null;
        Enumerator lv_type_0_0 = null;



        	enterRule();

        try {
            // InternalPf.g:411:2: ( ( ( (lv_type_0_0= rulePhenomenonType ) )? ( (lv_isControlled_1_0= '!' ) )? ( (lv_name_2_0= RULE_ID ) ) ( (otherlv_3= ':' )? ( (lv_description_4_0= RULE_STRING ) ) )? ) )
            // InternalPf.g:412:2: ( ( (lv_type_0_0= rulePhenomenonType ) )? ( (lv_isControlled_1_0= '!' ) )? ( (lv_name_2_0= RULE_ID ) ) ( (otherlv_3= ':' )? ( (lv_description_4_0= RULE_STRING ) ) )? )
            {
            // InternalPf.g:412:2: ( ( (lv_type_0_0= rulePhenomenonType ) )? ( (lv_isControlled_1_0= '!' ) )? ( (lv_name_2_0= RULE_ID ) ) ( (otherlv_3= ':' )? ( (lv_description_4_0= RULE_STRING ) ) )? )
            // InternalPf.g:413:3: ( (lv_type_0_0= rulePhenomenonType ) )? ( (lv_isControlled_1_0= '!' ) )? ( (lv_name_2_0= RULE_ID ) ) ( (otherlv_3= ':' )? ( (lv_description_4_0= RULE_STRING ) ) )?
            {
            // InternalPf.g:413:3: ( (lv_type_0_0= rulePhenomenonType ) )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( ((LA9_0>=30 && LA9_0<=33)) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalPf.g:414:4: (lv_type_0_0= rulePhenomenonType )
                    {
                    // InternalPf.g:414:4: (lv_type_0_0= rulePhenomenonType )
                    // InternalPf.g:415:5: lv_type_0_0= rulePhenomenonType
                    {

                    					newCompositeNode(grammarAccess.getPhenomenonAccess().getTypePhenomenonTypeEnumRuleCall_0_0());
                    				
                    pushFollow(FOLLOW_16);
                    lv_type_0_0=rulePhenomenonType();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getPhenomenonRule());
                    					}
                    					set(
                    						current,
                    						"type",
                    						lv_type_0_0,
                    						"pf.Pf.PhenomenonType");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalPf.g:432:3: ( (lv_isControlled_1_0= '!' ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==21) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalPf.g:433:4: (lv_isControlled_1_0= '!' )
                    {
                    // InternalPf.g:433:4: (lv_isControlled_1_0= '!' )
                    // InternalPf.g:434:5: lv_isControlled_1_0= '!'
                    {
                    lv_isControlled_1_0=(Token)match(input,21,FOLLOW_3); 

                    					newLeafNode(lv_isControlled_1_0, grammarAccess.getPhenomenonAccess().getIsControlledExclamationMarkKeyword_1_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getPhenomenonRule());
                    					}
                    					setWithLastConsumed(current, "isControlled", true, "!");
                    				

                    }


                    }
                    break;

            }

            // InternalPf.g:446:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalPf.g:447:4: (lv_name_2_0= RULE_ID )
            {
            // InternalPf.g:447:4: (lv_name_2_0= RULE_ID )
            // InternalPf.g:448:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_17); 

            					newLeafNode(lv_name_2_0, grammarAccess.getPhenomenonAccess().getNameIDTerminalRuleCall_2_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPhenomenonRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_2_0,
            						"pf.Pf.ID");
            				

            }


            }

            // InternalPf.g:464:3: ( (otherlv_3= ':' )? ( (lv_description_4_0= RULE_STRING ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==RULE_STRING||LA12_0==14) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalPf.g:465:4: (otherlv_3= ':' )? ( (lv_description_4_0= RULE_STRING ) )
                    {
                    // InternalPf.g:465:4: (otherlv_3= ':' )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==14) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // InternalPf.g:466:5: otherlv_3= ':'
                            {
                            otherlv_3=(Token)match(input,14,FOLLOW_8); 

                            					newLeafNode(otherlv_3, grammarAccess.getPhenomenonAccess().getColonKeyword_3_0());
                            				

                            }
                            break;

                    }

                    // InternalPf.g:471:4: ( (lv_description_4_0= RULE_STRING ) )
                    // InternalPf.g:472:5: (lv_description_4_0= RULE_STRING )
                    {
                    // InternalPf.g:472:5: (lv_description_4_0= RULE_STRING )
                    // InternalPf.g:473:6: lv_description_4_0= RULE_STRING
                    {
                    lv_description_4_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    						newLeafNode(lv_description_4_0, grammarAccess.getPhenomenonAccess().getDescriptionSTRINGTerminalRuleCall_3_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getPhenomenonRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"description",
                    							lv_description_4_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePhenomenon"


    // $ANTLR start "entryRuleLink"
    // InternalPf.g:494:1: entryRuleLink returns [EObject current=null] : iv_ruleLink= ruleLink EOF ;
    public final EObject entryRuleLink() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleLink = null;


        try {
            // InternalPf.g:494:45: (iv_ruleLink= ruleLink EOF )
            // InternalPf.g:495:2: iv_ruleLink= ruleLink EOF
            {
             newCompositeNode(grammarAccess.getLinkRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleLink=ruleLink();

            state._fsp--;

             current =iv_ruleLink; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleLink"


    // $ANTLR start "ruleLink"
    // InternalPf.g:501:1: ruleLink returns [EObject current=null] : ( ( (otherlv_0= RULE_ID ) ) ( (lv_type_1_0= ruleLinkType ) ) ( (otherlv_2= RULE_ID ) ) (otherlv_3= '{' ( (lv_phenomena_4_0= rulePhenomenon ) ) (otherlv_5= ',' ( (lv_phenomena_6_0= rulePhenomenon ) ) )* otherlv_7= '}' )? ( (otherlv_8= ':' )? ( (lv_description_9_0= RULE_STRING ) ) )? ) ;
    public final EObject ruleLink() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token lv_description_9_0=null;
        Enumerator lv_type_1_0 = null;

        EObject lv_phenomena_4_0 = null;

        EObject lv_phenomena_6_0 = null;



        	enterRule();

        try {
            // InternalPf.g:507:2: ( ( ( (otherlv_0= RULE_ID ) ) ( (lv_type_1_0= ruleLinkType ) ) ( (otherlv_2= RULE_ID ) ) (otherlv_3= '{' ( (lv_phenomena_4_0= rulePhenomenon ) ) (otherlv_5= ',' ( (lv_phenomena_6_0= rulePhenomenon ) ) )* otherlv_7= '}' )? ( (otherlv_8= ':' )? ( (lv_description_9_0= RULE_STRING ) ) )? ) )
            // InternalPf.g:508:2: ( ( (otherlv_0= RULE_ID ) ) ( (lv_type_1_0= ruleLinkType ) ) ( (otherlv_2= RULE_ID ) ) (otherlv_3= '{' ( (lv_phenomena_4_0= rulePhenomenon ) ) (otherlv_5= ',' ( (lv_phenomena_6_0= rulePhenomenon ) ) )* otherlv_7= '}' )? ( (otherlv_8= ':' )? ( (lv_description_9_0= RULE_STRING ) ) )? )
            {
            // InternalPf.g:508:2: ( ( (otherlv_0= RULE_ID ) ) ( (lv_type_1_0= ruleLinkType ) ) ( (otherlv_2= RULE_ID ) ) (otherlv_3= '{' ( (lv_phenomena_4_0= rulePhenomenon ) ) (otherlv_5= ',' ( (lv_phenomena_6_0= rulePhenomenon ) ) )* otherlv_7= '}' )? ( (otherlv_8= ':' )? ( (lv_description_9_0= RULE_STRING ) ) )? )
            // InternalPf.g:509:3: ( (otherlv_0= RULE_ID ) ) ( (lv_type_1_0= ruleLinkType ) ) ( (otherlv_2= RULE_ID ) ) (otherlv_3= '{' ( (lv_phenomena_4_0= rulePhenomenon ) ) (otherlv_5= ',' ( (lv_phenomena_6_0= rulePhenomenon ) ) )* otherlv_7= '}' )? ( (otherlv_8= ':' )? ( (lv_description_9_0= RULE_STRING ) ) )?
            {
            // InternalPf.g:509:3: ( (otherlv_0= RULE_ID ) )
            // InternalPf.g:510:4: (otherlv_0= RULE_ID )
            {
            // InternalPf.g:510:4: (otherlv_0= RULE_ID )
            // InternalPf.g:511:5: otherlv_0= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLinkRule());
            					}
            				
            otherlv_0=(Token)match(input,RULE_ID,FOLLOW_18); 

            					newLeafNode(otherlv_0, grammarAccess.getLinkAccess().getFromNodeCrossReference_0_0());
            				

            }


            }

            // InternalPf.g:522:3: ( (lv_type_1_0= ruleLinkType ) )
            // InternalPf.g:523:4: (lv_type_1_0= ruleLinkType )
            {
            // InternalPf.g:523:4: (lv_type_1_0= ruleLinkType )
            // InternalPf.g:524:5: lv_type_1_0= ruleLinkType
            {

            					newCompositeNode(grammarAccess.getLinkAccess().getTypeLinkTypeEnumRuleCall_1_0());
            				
            pushFollow(FOLLOW_3);
            lv_type_1_0=ruleLinkType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getLinkRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_1_0,
            						"pf.Pf.LinkType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalPf.g:541:3: ( (otherlv_2= RULE_ID ) )
            // InternalPf.g:542:4: (otherlv_2= RULE_ID )
            {
            // InternalPf.g:542:4: (otherlv_2= RULE_ID )
            // InternalPf.g:543:5: otherlv_2= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getLinkRule());
            					}
            				
            otherlv_2=(Token)match(input,RULE_ID,FOLLOW_7); 

            					newLeafNode(otherlv_2, grammarAccess.getLinkAccess().getToNodeCrossReference_2_0());
            				

            }


            }

            // InternalPf.g:554:3: (otherlv_3= '{' ( (lv_phenomena_4_0= rulePhenomenon ) ) (otherlv_5= ',' ( (lv_phenomena_6_0= rulePhenomenon ) ) )* otherlv_7= '}' )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==15) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalPf.g:555:4: otherlv_3= '{' ( (lv_phenomena_4_0= rulePhenomenon ) ) (otherlv_5= ',' ( (lv_phenomena_6_0= rulePhenomenon ) ) )* otherlv_7= '}'
                    {
                    otherlv_3=(Token)match(input,15,FOLLOW_12); 

                    				newLeafNode(otherlv_3, grammarAccess.getLinkAccess().getLeftCurlyBracketKeyword_3_0());
                    			
                    // InternalPf.g:559:4: ( (lv_phenomena_4_0= rulePhenomenon ) )
                    // InternalPf.g:560:5: (lv_phenomena_4_0= rulePhenomenon )
                    {
                    // InternalPf.g:560:5: (lv_phenomena_4_0= rulePhenomenon )
                    // InternalPf.g:561:6: lv_phenomena_4_0= rulePhenomenon
                    {

                    						newCompositeNode(grammarAccess.getLinkAccess().getPhenomenaPhenomenonParserRuleCall_3_1_0());
                    					
                    pushFollow(FOLLOW_19);
                    lv_phenomena_4_0=rulePhenomenon();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getLinkRule());
                    						}
                    						add(
                    							current,
                    							"phenomena",
                    							lv_phenomena_4_0,
                    							"pf.Pf.Phenomenon");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalPf.g:578:4: (otherlv_5= ',' ( (lv_phenomena_6_0= rulePhenomenon ) ) )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==16) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // InternalPf.g:579:5: otherlv_5= ',' ( (lv_phenomena_6_0= rulePhenomenon ) )
                    	    {
                    	    otherlv_5=(Token)match(input,16,FOLLOW_12); 

                    	    					newLeafNode(otherlv_5, grammarAccess.getLinkAccess().getCommaKeyword_3_2_0());
                    	    				
                    	    // InternalPf.g:583:5: ( (lv_phenomena_6_0= rulePhenomenon ) )
                    	    // InternalPf.g:584:6: (lv_phenomena_6_0= rulePhenomenon )
                    	    {
                    	    // InternalPf.g:584:6: (lv_phenomena_6_0= rulePhenomenon )
                    	    // InternalPf.g:585:7: lv_phenomena_6_0= rulePhenomenon
                    	    {

                    	    							newCompositeNode(grammarAccess.getLinkAccess().getPhenomenaPhenomenonParserRuleCall_3_2_1_0());
                    	    						
                    	    pushFollow(FOLLOW_19);
                    	    lv_phenomena_6_0=rulePhenomenon();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getLinkRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"phenomena",
                    	    								lv_phenomena_6_0,
                    	    								"pf.Pf.Phenomenon");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);

                    otherlv_7=(Token)match(input,20,FOLLOW_17); 

                    				newLeafNode(otherlv_7, grammarAccess.getLinkAccess().getRightCurlyBracketKeyword_3_3());
                    			

                    }
                    break;

            }

            // InternalPf.g:608:3: ( (otherlv_8= ':' )? ( (lv_description_9_0= RULE_STRING ) ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==RULE_STRING||LA16_0==14) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalPf.g:609:4: (otherlv_8= ':' )? ( (lv_description_9_0= RULE_STRING ) )
                    {
                    // InternalPf.g:609:4: (otherlv_8= ':' )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==14) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalPf.g:610:5: otherlv_8= ':'
                            {
                            otherlv_8=(Token)match(input,14,FOLLOW_8); 

                            					newLeafNode(otherlv_8, grammarAccess.getLinkAccess().getColonKeyword_4_0());
                            				

                            }
                            break;

                    }

                    // InternalPf.g:615:4: ( (lv_description_9_0= RULE_STRING ) )
                    // InternalPf.g:616:5: (lv_description_9_0= RULE_STRING )
                    {
                    // InternalPf.g:616:5: (lv_description_9_0= RULE_STRING )
                    // InternalPf.g:617:6: lv_description_9_0= RULE_STRING
                    {
                    lv_description_9_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

                    						newLeafNode(lv_description_9_0, grammarAccess.getLinkAccess().getDescriptionSTRINGTerminalRuleCall_4_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getLinkRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"description",
                    							lv_description_9_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLink"


    // $ANTLR start "ruleNodeType"
    // InternalPf.g:638:1: ruleNodeType returns [Enumerator current=null] : ( (enumLiteral_0= 'R' ) | (enumLiteral_1= 'M' ) | (enumLiteral_2= 'B' ) | (enumLiteral_3= 'X' ) | (enumLiteral_4= 'C' ) | (enumLiteral_5= 'D' ) | (enumLiteral_6= 'P' ) | (enumLiteral_7= '?' ) ) ;
    public final Enumerator ruleNodeType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;
        Token enumLiteral_7=null;


        	enterRule();

        try {
            // InternalPf.g:644:2: ( ( (enumLiteral_0= 'R' ) | (enumLiteral_1= 'M' ) | (enumLiteral_2= 'B' ) | (enumLiteral_3= 'X' ) | (enumLiteral_4= 'C' ) | (enumLiteral_5= 'D' ) | (enumLiteral_6= 'P' ) | (enumLiteral_7= '?' ) ) )
            // InternalPf.g:645:2: ( (enumLiteral_0= 'R' ) | (enumLiteral_1= 'M' ) | (enumLiteral_2= 'B' ) | (enumLiteral_3= 'X' ) | (enumLiteral_4= 'C' ) | (enumLiteral_5= 'D' ) | (enumLiteral_6= 'P' ) | (enumLiteral_7= '?' ) )
            {
            // InternalPf.g:645:2: ( (enumLiteral_0= 'R' ) | (enumLiteral_1= 'M' ) | (enumLiteral_2= 'B' ) | (enumLiteral_3= 'X' ) | (enumLiteral_4= 'C' ) | (enumLiteral_5= 'D' ) | (enumLiteral_6= 'P' ) | (enumLiteral_7= '?' ) )
            int alt17=8;
            switch ( input.LA(1) ) {
            case 22:
                {
                alt17=1;
                }
                break;
            case 23:
                {
                alt17=2;
                }
                break;
            case 24:
                {
                alt17=3;
                }
                break;
            case 25:
                {
                alt17=4;
                }
                break;
            case 26:
                {
                alt17=5;
                }
                break;
            case 27:
                {
                alt17=6;
                }
                break;
            case 28:
                {
                alt17=7;
                }
                break;
            case 29:
                {
                alt17=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // InternalPf.g:646:3: (enumLiteral_0= 'R' )
                    {
                    // InternalPf.g:646:3: (enumLiteral_0= 'R' )
                    // InternalPf.g:647:4: enumLiteral_0= 'R'
                    {
                    enumLiteral_0=(Token)match(input,22,FOLLOW_2); 

                    				current = grammarAccess.getNodeTypeAccess().getREQUIREMENTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getNodeTypeAccess().getREQUIREMENTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalPf.g:654:3: (enumLiteral_1= 'M' )
                    {
                    // InternalPf.g:654:3: (enumLiteral_1= 'M' )
                    // InternalPf.g:655:4: enumLiteral_1= 'M'
                    {
                    enumLiteral_1=(Token)match(input,23,FOLLOW_2); 

                    				current = grammarAccess.getNodeTypeAccess().getMACHINEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getNodeTypeAccess().getMACHINEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalPf.g:662:3: (enumLiteral_2= 'B' )
                    {
                    // InternalPf.g:662:3: (enumLiteral_2= 'B' )
                    // InternalPf.g:663:4: enumLiteral_2= 'B'
                    {
                    enumLiteral_2=(Token)match(input,24,FOLLOW_2); 

                    				current = grammarAccess.getNodeTypeAccess().getBIDDABLEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getNodeTypeAccess().getBIDDABLEEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalPf.g:670:3: (enumLiteral_3= 'X' )
                    {
                    // InternalPf.g:670:3: (enumLiteral_3= 'X' )
                    // InternalPf.g:671:4: enumLiteral_3= 'X'
                    {
                    enumLiteral_3=(Token)match(input,25,FOLLOW_2); 

                    				current = grammarAccess.getNodeTypeAccess().getLEXICALEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getNodeTypeAccess().getLEXICALEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalPf.g:678:3: (enumLiteral_4= 'C' )
                    {
                    // InternalPf.g:678:3: (enumLiteral_4= 'C' )
                    // InternalPf.g:679:4: enumLiteral_4= 'C'
                    {
                    enumLiteral_4=(Token)match(input,26,FOLLOW_2); 

                    				current = grammarAccess.getNodeTypeAccess().getCAUSALEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getNodeTypeAccess().getCAUSALEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalPf.g:686:3: (enumLiteral_5= 'D' )
                    {
                    // InternalPf.g:686:3: (enumLiteral_5= 'D' )
                    // InternalPf.g:687:4: enumLiteral_5= 'D'
                    {
                    enumLiteral_5=(Token)match(input,27,FOLLOW_2); 

                    				current = grammarAccess.getNodeTypeAccess().getDESIGNEDEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getNodeTypeAccess().getDESIGNEDEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalPf.g:694:3: (enumLiteral_6= 'P' )
                    {
                    // InternalPf.g:694:3: (enumLiteral_6= 'P' )
                    // InternalPf.g:695:4: enumLiteral_6= 'P'
                    {
                    enumLiteral_6=(Token)match(input,28,FOLLOW_2); 

                    				current = grammarAccess.getNodeTypeAccess().getPHYSICALEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getNodeTypeAccess().getPHYSICALEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalPf.g:702:3: (enumLiteral_7= '?' )
                    {
                    // InternalPf.g:702:3: (enumLiteral_7= '?' )
                    // InternalPf.g:703:4: enumLiteral_7= '?'
                    {
                    enumLiteral_7=(Token)match(input,29,FOLLOW_2); 

                    				current = grammarAccess.getNodeTypeAccess().getCONCERNEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_7, grammarAccess.getNodeTypeAccess().getCONCERNEnumLiteralDeclaration_7());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNodeType"


    // $ANTLR start "rulePhenomenonType"
    // InternalPf.g:713:1: rulePhenomenonType returns [Enumerator current=null] : ( (enumLiteral_0= 'phenomenon' ) | (enumLiteral_1= 'event' ) | (enumLiteral_2= 'state' ) | (enumLiteral_3= 'value' ) ) ;
    public final Enumerator rulePhenomenonType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalPf.g:719:2: ( ( (enumLiteral_0= 'phenomenon' ) | (enumLiteral_1= 'event' ) | (enumLiteral_2= 'state' ) | (enumLiteral_3= 'value' ) ) )
            // InternalPf.g:720:2: ( (enumLiteral_0= 'phenomenon' ) | (enumLiteral_1= 'event' ) | (enumLiteral_2= 'state' ) | (enumLiteral_3= 'value' ) )
            {
            // InternalPf.g:720:2: ( (enumLiteral_0= 'phenomenon' ) | (enumLiteral_1= 'event' ) | (enumLiteral_2= 'state' ) | (enumLiteral_3= 'value' ) )
            int alt18=4;
            switch ( input.LA(1) ) {
            case 30:
                {
                alt18=1;
                }
                break;
            case 31:
                {
                alt18=2;
                }
                break;
            case 32:
                {
                alt18=3;
                }
                break;
            case 33:
                {
                alt18=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // InternalPf.g:721:3: (enumLiteral_0= 'phenomenon' )
                    {
                    // InternalPf.g:721:3: (enumLiteral_0= 'phenomenon' )
                    // InternalPf.g:722:4: enumLiteral_0= 'phenomenon'
                    {
                    enumLiteral_0=(Token)match(input,30,FOLLOW_2); 

                    				current = grammarAccess.getPhenomenonTypeAccess().getUNSPECIFIEDEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getPhenomenonTypeAccess().getUNSPECIFIEDEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalPf.g:729:3: (enumLiteral_1= 'event' )
                    {
                    // InternalPf.g:729:3: (enumLiteral_1= 'event' )
                    // InternalPf.g:730:4: enumLiteral_1= 'event'
                    {
                    enumLiteral_1=(Token)match(input,31,FOLLOW_2); 

                    				current = grammarAccess.getPhenomenonTypeAccess().getEVENTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getPhenomenonTypeAccess().getEVENTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalPf.g:737:3: (enumLiteral_2= 'state' )
                    {
                    // InternalPf.g:737:3: (enumLiteral_2= 'state' )
                    // InternalPf.g:738:4: enumLiteral_2= 'state'
                    {
                    enumLiteral_2=(Token)match(input,32,FOLLOW_2); 

                    				current = grammarAccess.getPhenomenonTypeAccess().getSTATEEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getPhenomenonTypeAccess().getSTATEEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalPf.g:745:3: (enumLiteral_3= 'value' )
                    {
                    // InternalPf.g:745:3: (enumLiteral_3= 'value' )
                    // InternalPf.g:746:4: enumLiteral_3= 'value'
                    {
                    enumLiteral_3=(Token)match(input,33,FOLLOW_2); 

                    				current = grammarAccess.getPhenomenonTypeAccess().getVALUEEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getPhenomenonTypeAccess().getVALUEEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePhenomenonType"


    // $ANTLR start "ruleLinkType"
    // InternalPf.g:756:1: ruleLinkType returns [Enumerator current=null] : ( (enumLiteral_0= '--' ) | (enumLiteral_1= '~~' ) | (enumLiteral_2= '<~' ) | (enumLiteral_3= '->' ) | (enumLiteral_4= '~>' ) ) ;
    public final Enumerator ruleLinkType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalPf.g:762:2: ( ( (enumLiteral_0= '--' ) | (enumLiteral_1= '~~' ) | (enumLiteral_2= '<~' ) | (enumLiteral_3= '->' ) | (enumLiteral_4= '~>' ) ) )
            // InternalPf.g:763:2: ( (enumLiteral_0= '--' ) | (enumLiteral_1= '~~' ) | (enumLiteral_2= '<~' ) | (enumLiteral_3= '->' ) | (enumLiteral_4= '~>' ) )
            {
            // InternalPf.g:763:2: ( (enumLiteral_0= '--' ) | (enumLiteral_1= '~~' ) | (enumLiteral_2= '<~' ) | (enumLiteral_3= '->' ) | (enumLiteral_4= '~>' ) )
            int alt19=5;
            switch ( input.LA(1) ) {
            case 34:
                {
                alt19=1;
                }
                break;
            case 35:
                {
                alt19=2;
                }
                break;
            case 36:
                {
                alt19=3;
                }
                break;
            case 37:
                {
                alt19=4;
                }
                break;
            case 38:
                {
                alt19=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // InternalPf.g:764:3: (enumLiteral_0= '--' )
                    {
                    // InternalPf.g:764:3: (enumLiteral_0= '--' )
                    // InternalPf.g:765:4: enumLiteral_0= '--'
                    {
                    enumLiteral_0=(Token)match(input,34,FOLLOW_2); 

                    				current = grammarAccess.getLinkTypeAccess().getINTERFACEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getLinkTypeAccess().getINTERFACEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalPf.g:772:3: (enumLiteral_1= '~~' )
                    {
                    // InternalPf.g:772:3: (enumLiteral_1= '~~' )
                    // InternalPf.g:773:4: enumLiteral_1= '~~'
                    {
                    enumLiteral_1=(Token)match(input,35,FOLLOW_2); 

                    				current = grammarAccess.getLinkTypeAccess().getREFERENCEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getLinkTypeAccess().getREFERENCEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalPf.g:780:3: (enumLiteral_2= '<~' )
                    {
                    // InternalPf.g:780:3: (enumLiteral_2= '<~' )
                    // InternalPf.g:781:4: enumLiteral_2= '<~'
                    {
                    enumLiteral_2=(Token)match(input,36,FOLLOW_2); 

                    				current = grammarAccess.getLinkTypeAccess().getCONSTRAINTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getLinkTypeAccess().getCONSTRAINTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalPf.g:788:3: (enumLiteral_3= '->' )
                    {
                    // InternalPf.g:788:3: (enumLiteral_3= '->' )
                    // InternalPf.g:789:4: enumLiteral_3= '->'
                    {
                    enumLiteral_3=(Token)match(input,37,FOLLOW_2); 

                    				current = grammarAccess.getLinkTypeAccess().getCONCERNEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getLinkTypeAccess().getCONCERNEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalPf.g:796:3: (enumLiteral_4= '~>' )
                    {
                    // InternalPf.g:796:3: (enumLiteral_4= '~>' )
                    // InternalPf.g:797:4: enumLiteral_4= '~>'
                    {
                    enumLiteral_4=(Token)match(input,38,FOLLOW_2); 

                    				current = grammarAccess.getLinkTypeAccess().getINV_CONSTRAINTEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getLinkTypeAccess().getINV_CONSTRAINTEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleLinkType"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000002012L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x000000003FC00000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x000000000000C022L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00000003C0321010L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000131000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x00000003C0200010L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000121000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000200010L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000004022L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000007C00000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000110000L});

}