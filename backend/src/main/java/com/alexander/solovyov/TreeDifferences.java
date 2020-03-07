package com.alexander.solovyov;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.tree.TreeContext;

import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

final public class TreeDifferences extends JFrame {

    public TreeDifferences(String title,final TreeContext sourceContext, final TreeContext destContext,List<Action> actions,MappingStore mappings) {
        super(title);
        GumTreeDataFetcher fetcher;
        try {
            fetcher = new GumTreeDataFetcher(sourceContext, destContext,actions,mappings);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to init GumTree!");
            return;
        }
        setBounds(100, 100, 1200, 1200);

        final JTree leftPane = new JTree(fetcher.getSourceNode());
        leftPane.setCellRenderer(new AstTreeCellRenderer(true, fetcher));
        leftPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //expandTree 
        TreeNode root = (TreeNode) leftPane.getModel().getRoot();
        expandTree(leftPane, new TreePath(root));

        final JTree rightPane = new JTree(fetcher.getDestNode());
        rightPane.setCellRenderer(new AstTreeCellRenderer(false, fetcher));
        rightPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //expandTree 
        TreeNode root2 = (TreeNode) rightPane.getModel().getRoot();
        expandTree(rightPane, new TreePath(root2));
        
        new MappedTreesListener(leftPane, rightPane, fetcher);

        final JSplitPane pane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(leftPane), new JScrollPane(rightPane));
        pane.setBorder(BorderFactory.createEmptyBorder(3, 4, 3, 4));
        add(pane);
        pane.setDividerLocation(0.5f);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void expandTree(JTree tree, TreePath parent) {

    	TreeNode node = (TreeNode) parent.getLastPathComponent();

    	if (node.getChildCount() >= 0) {

	    	for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
	
		    	TreeNode n = (TreeNode) e.nextElement();
		
		    	TreePath path = parent.pathByAddingChild(n);
		
		    	expandTree(tree, path);
	
	    	}

    	}

    	tree.expandPath(parent);

    }
}
