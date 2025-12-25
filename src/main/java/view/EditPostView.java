package view;

import controller.PostController;
import model.Post;
import model.User;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.io.File;

public class EditPostView extends JFrame {
    private User currentUser;
    private Post post;
    private Runnable refreshCallback;
    private PostController postController;

    private JTextField titleField;
    private JEditorPane contentEditor;
    private JTextField attachmentField;
    private JButton chooseFileButton;
    private JButton submitButton;

    public EditPostView(User user, Post post, Runnable refreshCallback) {
        this.currentUser = user;
        this.post = post;
        this.refreshCallback = refreshCallback;
        this.postController = new PostController();

        setTitle("编辑帖子 - " + post.getTitle());
        setSize(700, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 顶部标题
        JPanel top = new JPanel();
        top.add(new JLabel("标题："));
        titleField = new JTextField(post.getTitle(), 40);
        top.add(titleField);
        add(top, BorderLayout.NORTH);

        // 中间富文本编辑器 + 工具栏
        JPanel editorPanel = new JPanel(new BorderLayout());
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton boldButton = new JButton("加粗");
        boldButton.addActionListener(e -> applyStyle(StyleConstants.Bold, true));
        toolBar.add(boldButton);

        JButton italicButton = new JButton("斜体");
        italicButton.addActionListener(e -> applyStyle(StyleConstants.Italic, true));
        toolBar.add(italicButton);

        JButton redButton = new JButton("红色");
        redButton.addActionListener(e -> applyStyle(StyleConstants.Foreground, Color.RED));
        toolBar.add(redButton);

        JButton largeFontButton = new JButton("大字体");
        largeFontButton.addActionListener(e -> applyStyle(StyleConstants.FontSize, 24));
        toolBar.add(largeFontButton);

        editorPanel.add(toolBar, BorderLayout.NORTH);

        contentEditor = new JEditorPane();
        contentEditor.setContentType("text/html");
        contentEditor.setEditable(true);
        contentEditor.setEditorKit(new HTMLEditorKit());
        contentEditor.setText(post.getContent());
        editorPanel.add(new JScrollPane(contentEditor), BorderLayout.CENTER);
        add(editorPanel, BorderLayout.CENTER);

        // 底部附件和按钮
        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(new JLabel("附件："));
        attachmentField = new JTextField(post.getAttachment() != null ? post.getAttachment() : "", 30);
        attachmentField.setEditable(false);
        bottom.add(attachmentField);

        chooseFileButton = new JButton("重新选择文件");
        chooseFileButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                attachmentField.setText(file.getAbsolutePath());
            }
        });
        bottom.add(chooseFileButton);

        submitButton = new JButton("保存修改");
        submitButton.addActionListener(e -> updatePost());
        bottom.add(submitButton);

        add(bottom, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void applyStyle(Object attribute, Object value) {
        StyledDocument doc = (StyledDocument) contentEditor.getDocument();
        int start = contentEditor.getSelectionStart();
        int end = contentEditor.getSelectionEnd();
        if (start == end) return;

        MutableAttributeSet attrs = new SimpleAttributeSet();
        attrs.addAttribute(attribute, value);
        doc.setCharacterAttributes(start, end - start, attrs, false);
    }

    private void updatePost() {
        post.setTitle(titleField.getText().trim());
        post.setContent(contentEditor.getText().trim());
        post.setAttachment(attachmentField.getText());

        if (post.getTitle().isEmpty() || post.getContent().isEmpty()) {
            JOptionPane.showMessageDialog(this, "标题和内容不能为空！");
            return;
        }

        boolean success = postController.updatePost(post);
        if (success) {
            JOptionPane.showMessageDialog(this, "修改成功！");
            dispose();
            if (refreshCallback != null) refreshCallback.run();
        }
    }
}