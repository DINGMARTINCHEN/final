package patterns.visitor;

/**
 * 可被访问的元素接口
 */
public interface PostElement {
    /**
     * 接受访问者访问
     * @param visitor 访问者
     * @return 操作结果
     */
    String accept(PostVisitor visitor);
}