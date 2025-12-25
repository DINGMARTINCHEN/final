// src/patterns/decorator/AttachmentDecorator.java
package patterns.decorator;

import model.Post;

/**
 * 具体装饰器 - 为帖子添加附件信息
 */
public class AttachmentDecorator extends PostDecorator {
    private String attachment;

    public AttachmentDecorator(Post decoratedPost, String attachment) {
        super(decoratedPost);
        this.attachment = attachment;
    }

    @Override
    public String getContent() {
        String originalContent = decoratedPost.getContent();
        String attachmentHtml = String.format(
            "<br><br><hr><b>附件信息：</b><br>" +
            "文件名：%s<br>" +
            "<a href='file://%s'>点击打开附件</a>",
            getFileName(attachment), attachment
        );
        return originalContent + attachmentHtml;
    }

    private String getFileName(String filePath) {
        if (filePath == null || filePath.isEmpty()) return "无附件";
        int lastIndex = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\'));
        return lastIndex >= 0 ? filePath.substring(lastIndex + 1) : filePath;
    }

    // 重写其他方法，确保装饰器正常工作
    @Override
    public String getTitle() {
        return decoratedPost.getTitle();
    }

    @Override
    public int getId() {
        return decoratedPost.getId();
    }

    @Override
    public int getUserId() {
        return decoratedPost.getUserId();
    }

    @Override
    public int getBoardId() {
        return decoratedPost.getBoardId();
    }

    @Override
    public java.util.Date getCreatedAt() {
        return decoratedPost.getCreatedAt();
    }

    @Override
    public String getAttachment() {
        return decoratedPost.getAttachment();
    }

    @Override
    public int getViews() {
        return decoratedPost.getViews();
    }

    @Override
    public void setTitle(String title) {
        decoratedPost.setTitle(title);
    }

    @Override
    public void setContent(String content) {
        decoratedPost.setContent(content);
    }

    @Override
    public void setUserId(int userId) {
        decoratedPost.setUserId(userId);
    }

    @Override
    public void setBoardId(int boardId) {
        decoratedPost.setBoardId(boardId);
    }

    @Override
    public void setCreatedAt(java.util.Date createdAt) {
        decoratedPost.setCreatedAt(createdAt);
    }

    @Override
    public void setAttachment(String attachment) {
        decoratedPost.setAttachment(attachment);
    }

    @Override
    public void setViews(int views) {
        decoratedPost.setViews(views);
    }
}