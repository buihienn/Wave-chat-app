package com.wavechat.component;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {
    private String buttonType; // Xác định loại nút
    private final Color customColor = new Color(26, 41, 128);

    public ButtonRenderer(String buttonType) {
        this.buttonType = buttonType;
        setOpaque(true); // Đảm bảo nút hiển thị màu sắc đầy đủ
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Xác định nội dung của nút dựa trên buttonType
        setText(buttonType);
        setForeground(Color.WHITE);

        // Tùy chỉnh màu nền dựa trên trạng thái isSelected
        if (isSelected) {
            setBackground(Color.LIGHT_GRAY);
        } else {
            setBackground(customColor);
        }

        // Tùy chỉnh font
        setFont(new Font("Montserrat", Font.PLAIN, 12));
        return this;
    }

    // Lớp con bên trong để dễ gọi từng loại nút
    public static class Unfriend extends ButtonRenderer {
        public Unfriend() {
            super("Unfriend");
        }
    }

    public static class Block extends ButtonRenderer {
        public Block() {
            super("Block");
        }
    }

    public static class Accept extends ButtonRenderer {
        public Accept() {
            super("Accept");
        }
    }

    public static class Delete extends ButtonRenderer {
        public Delete() {
            super("Delete");
        }
    }
}