package com.joker.translation.entity;

import java.util.List;

/**
 * Created by Joker on 2016/6/21.
 */
public class TranslationResult
{
    public String from;
    public String to;
    public List<Trans> trans_result;

    public class Trans
    {
        public String src;
        public String dst;

        @Override
        public String toString()
        {
            return "Trans{" +
                    "src='" + src + '\'' +
                    ", dst='" + dst + '\'' +
                    '}';
        }
    }

    @Override
    public String toString()
    {
        return "TranslationResult{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", trans_result=" + trans_result +
                '}';
    }
}
