package com.alexander.solovyov;

import com.github.gumtreediff.tree.ITree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

final public class AstTreeCellRenderer extends DefaultTreeCellRenderer {
    private static Color DeleteColor = new Color(170, 30, 30);//red
    private static Color UpdateColor = new Color(138, 43, 226);
    private static Color MoveColor = new Color(30, 144, 255);//blue
    private static Color InsertColor = new Color(0, 128, 0);//green

    private boolean isSrc;
    private GumTreeDataFetcher fetcher;

    AstTreeCellRenderer(final boolean isSource, final GumTreeDataFetcher dataFetcher) {
        isSrc = isSource;
        fetcher = dataFetcher;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean expanded, boolean leaf,
                                                  int row, boolean hasFocus) {
        // Should always be ITree as nothing else is used to construct nodes in this app
        ITree node = (ITree) ((DefaultMutableTreeNode) value).getUserObject();
        final String cellText = isSrc ? fetcher.getSourcePrettyName(node) : fetcher.getDestPrettyName(node);
        super.getTreeCellRendererComponent(tree, cellText, sel, expanded, leaf, row, hasFocus);
        if (isSrc) {
            if (fetcher.isDeletedSource(node)) setForeground(DeleteColor);
            else if (fetcher.isUpdatedSource(node)) setForeground(UpdateColor);
            else if (fetcher.isMovedSource(node)) setForeground(MoveColor);
        } else {
            if (fetcher.isInsertedDest(node)) setForeground(InsertColor);
            else if (fetcher.isUpdatedDest(node)) setForeground(UpdateColor);
            else if (fetcher.isMovedDest(node)) setForeground(MoveColor);
        }
        return this;
    }
}
