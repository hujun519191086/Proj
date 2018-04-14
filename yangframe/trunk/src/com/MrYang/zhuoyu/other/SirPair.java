package com.MrYang.zhuoyu.other;

public class SirPair<T, K>
{
    public T first;
    public K second;

    public SirPair(T first, K second)
    {
        this.first = first;
        this.second = second;

    }

    @Override
    public String toString()
    {
        return "SirPair [first=" + first + ", second=" + second + "]";
    }

}
