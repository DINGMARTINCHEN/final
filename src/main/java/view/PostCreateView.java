package view;

import controller.PostController;
import model.Post;
import model.User;
import patterns.factory.PostFactory;
import patterns.factory.PostFactorySelector;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.io.File;

public class PostCreateView extends JFrame {
    private User currentUser;
    private int boardId;
    private Runnable refreshCallback;
    private PostController postController;

    private JTextField titleField;
    private JEditorPane contentEditor;
    private JTextField attachmentField;
    private JButton chooseFileButton;
    private JButton submitButton;
    private JComboBox<String> postTypeComboBox;

    public PostCreateView(User user, int boardId, Runnable refreshCallback) {
        this.currentUser = user;
        this.boardId = boardId;
        this.refreshCallback = refreshCallback;
        this.postController = new PostController();

        setTitle("发布新帖子");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 顶部标题和帖子类型选择
        JPanel top = new JPanel(new BorderLayout());
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(new JLabel("标题："));
        titleField = new JTextField(30);
        titlePanel.add(titleField);

        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        typePanel.add(new JLabel("帖子类型："));
        postTypeComboBox = new JComboBox<>(getAvailablePostTypes(user));
        typePanel.add(postTypeComboBox);

        top.add(titlePanel, BorderLayout.NORTH);
        top.add(typePanel, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        // 中间内容：富文本编辑器 + 工具栏
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
        contentEditor.setEditorKit(new HTMLEditorKit());
        editorPanel.add(new JScrollPane(contentEditor), BorderLayout.CENTER);
        add(editorPanel, BorderLayout.CENTER);

        // 底部附件和按钮
        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(new JLabel("附件："));
        attachmentField = new JTextField(30);
        attachmentField.setEditable(false);
        bottom.add(attachmentField);

        chooseFileButton = new JButton("选择文件");
        chooseFileButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                attachmentField.setText(file.getAbsolutePath());
            }
        });
        bottom.add(chooseFileButton);

        submitButton = new JButton("发布帖子");
        submitButton.addActionListener(e -> publishPost());
        bottom.add(submitButton);

        add(bottom, BorderLayout.SOUTH);
        setVisible(true);
    }

    private String[] getAvailablePostTypes(User user) {
        if (isAdminOrModerator(user)) {
            return new String[]{"普通帖子", "置顶帖子", "公告帖子"};
        } else {
            return new String[]{"普通帖子"};
        }
    }

    private boolean isAdminOrModerator(User user) {
        String role = user.getRole();
        return "admin".equals(role) || "moderator".equals(role);
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

    private void publishPost() {
        String title = titleField.getText().trim();
        String content = contentEditor.getText().trim();
        String attachment = attachmentField.getText();
        String postType = (String) postTypeComboBox.getSelectedItem();

        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "标题和内容不能为空！");
            return;
        }

        boolean success = postController.createPost(title, content, currentUser.getId(),
                boardId, attachment, postType, currentUser);

        if (success) {
            JOptionPane.showMessageDialog(this, "发帖成功！");
            dispose();
            if (refreshCallback != null) refreshCallback.run();
        }
    }
}