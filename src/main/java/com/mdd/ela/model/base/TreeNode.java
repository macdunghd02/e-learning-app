package com.mdd.ela.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author TruongVd
 * @date 06/10/2023
 * @project mci-backend
 */

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TreeNode {
    private boolean active;
    private String icon;
    private String path;
    private String link;
    private List<TreeNode> children;
    private String title;
    private String order;
    @JsonProperty("menu_id")
    private int menuId;

    public void addChild(TreeNode child) {
        children.add(child);
    }
    public void sortChildrenByOrder() {
        if (children != null) {
            children.sort((node1, node2) -> {
                String order1 = node1.getOrder();
                String order2 = node2.getOrder();

                // Nếu cả hai order đều không null
                if (order1 != null && order2 != null) {
                    order1 = order1.replaceAll("[^\\d.]", "");
                    order2 = order2.replaceAll("[^\\d.]", "");
                    String[] parts1 = order1.split("\\.");
                    String[] parts2 = order2.split("\\.");

                    // So sánh từng phần tử của mỗi chuỗi order
                    for (int i = 0; i < Math.min(parts1.length, parts2.length); i++) {
                        int partComparison = Integer.compare(Integer.parseInt(parts1[i]), Integer.parseInt(parts2[i]));
                        if (partComparison != 0) {
                            return partComparison;
                        }
                    }

                    // Nếu các phần tử cho đến cuối đều giống nhau, thì so sánh theo độ dài của chuỗi
                    return Integer.compare(parts1.length, parts2.length);
                }
                // Xử lý trường hợp một trong hai order là null
                else if (order1 == null && order2 != null) {
                    return 1; // node1 lớn hơn node2
                } else if (order1 != null && order2 == null) {
                    return -1; // node1 nhỏ hơn node2
                } else {
                    return 0; // Cả hai bằng nhau
                }
            });
            for (TreeNode child : children) {
                child.sortChildrenByOrder();
            }
        }
    }
}
