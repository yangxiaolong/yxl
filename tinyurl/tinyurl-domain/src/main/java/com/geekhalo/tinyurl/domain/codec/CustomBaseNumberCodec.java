package com.geekhalo.tinyurl.domain.codec;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Stack;

public class CustomBaseNumberCodec implements NumberCodec {
    private static final char[] DEFAULT_LETTERS = {
            'q', 'w', 'e', 'r', 't', 'y', 'u',
            'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z',
            'x', 'c', 'v', 'b', 'n', 'm', '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P',
            'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V',
            'B', 'N', 'M'};

    // 字符数组，每一位对应一个 数值，从 0 开始
    private final char[] letters;
    // 总进制数，也就是 letters 数组长度
    private final int base;
    // 每个字符对于的值
    private final Map<Character, Integer> valueMap;

    public CustomBaseNumberCodec() {
        this(DEFAULT_LETTERS);
    }

    public CustomBaseNumberCodec(char[] letters) {
        this.letters = letters;
        this.base = letters.length;
        this.valueMap = Maps.newHashMapWithExpectedSize(base);
        for (int i = 0; i < this.base; i++) {
            Character character = this.letters[i];
            Integer index = this.valueMap.get(character);
            if (index != null) {
                // 存在重复的字符，配置错误
                throw new IllegalArgumentException(character + "is not uniq");
            }
            // 构建字符与值的映射关系
            this.valueMap.put(character, i);
        }
    }

    /**
     * 将字符转换为对应的值，对于未识别字符，直接抛出异常
     *
     * @param c
     * @return
     */
    private int convertToIndex(Character c) {
        Integer index = this.valueMap.get(c);
        if (index != null) {
            return index;
        }
        throw new RuntimeException("Unknown Letter " + c);
    }

    @Override
    public String encode(Long id) {
        Long rest = id;

        // 记录每一位的字符
        Stack<Character> stack = new Stack<>();
        while (rest != 0) {
            // 计算末尾位的 value
            int value = (int) (rest - (rest / base) * base);
            // 记录末尾位对于的字符
            stack.add(letters[value]);
            // 向前移位
            rest = rest / base;
        }

        // 将字符串逆序，获得最终结果
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    @Override
    public Long decode(String str) {
        long multiple = 1;
        long result = 0;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            // 字符最后位
            int latestIndex = chars.length - i - 1;
            // 最后位对应的字符
            Character c = chars[latestIndex];
            // 获取字符对应的 value
            int value = convertToIndex(c);
            // 累计结果
            result += value * multiple;
            // 整体向左移位
            multiple = multiple * base;
        }
        return result;
    }

    public static void main(String[] args) {
        CustomBaseNumberCodec codec = new CustomBaseNumberCodec();
        System.out.println(codec.encode(45L));
    }
}
