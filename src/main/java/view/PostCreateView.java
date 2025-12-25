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
    private Runnable refreshCallback;
    private PostController postController;

    private JTextField titleField;
    private JEditorPane contentEditor;
    private JTextField attachmentField;
    private JButton chooseFileButton;
    private JButton submitButton;
    private JComboBox<String> postTypeComboBox;
    private JComboBox<String> boardComboBox;  // 新增：版块选择下拉框

    public PostCreateView(User user, Runnable refreshCallback) {
        this.currentUser = user;
        this.refreshCallback = refreshCallback;
        this.postController = new PostController();

        setTitle("发布新帖子");
        setSize(650, 550);  // 增加宽度以适应更多控件
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 顶部面板：标题和版块选择
        JPanel top = new JPanel(new GridLayout(2, 2, 10, 10));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 第一行：标题
        top.add(new JLabel("标题：", JLabel.RIGHT));
        titleField = new JTextField();
        top.add(titleField);

        // 第二行：版块选择
        top.add(new JLabel("选择版块：", JLabel.RIGHT));
        boardComboBox = new JComboBox<>(new String[]{"技术支持", "学习交流", "休闲娱乐", "校园生活"});
        top.add(boardComboBox);

        add(top, BorderLayout.NORTH);

        // 中部面板：帖子类型选择和富文本编辑器
        JPanel centerPanel = new JPanel(new BorderLayout());

        // 帖子类型选择
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        typePanel.add(new JLabel("帖子类型："));
        postTypeComboBox = new JComboBox<>(getAvailablePostTypes(user));
        typePanel.add(postTypeComboBox);
        centerPanel.add(typePanel, BorderLayout.NORTH);

        // 富文本编辑器和工具栏
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

        centerPanel.add(editorPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // 底部面板：附件和按钮
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 附件选择
        JPanel attachmentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        attachmentPanel.add(new JLabel("附件："));
        attachmentField = new JTextField(30);
        attachmentField.setEditable(false);
        attachmentPanel.add(attachmentField);

        chooseFileButton = new JButton("选择文件");
        chooseFileButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                attachmentField.setText(file.getAbsolutePath());
            }
        });
        attachmentPanel.add(chooseFileButton);
        bottom.add(attachmentPanel);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitButton = new JButton("发布帖子");
        submitButton.addActionListener(e -> publishPost());
        buttonPanel.add(submitButton);

        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        bottom.add(buttonPanel);
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

        // 获取选择的版块
        String boardName = (String) boardComboBox.getSelectedItem();
        int boardId = getBoardIdByName(boardName);

        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "标题和内容不能为空！");
            return;
        }

        if (boardId == -1) {
            JOptionPane.showMessageDialog(this, "请选择版块！");
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

    private int getBoardIdByName(String boardName) {
        switch (boardName) {
            case "技术支持": return 1;
            case "学习交流": return 2;
            case "休闲娱乐": return 3;
            case "校园生活": return 4;
            default: return -1;
        }
    }
}