package com.google.engedu.ghost;

import java.util.HashMap;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {

        String tempString = "";
        HashMap<String, TrieNode> temp = children;
        for(int i=0;i<s.length();i++)
        {

            TrieNode root = new TrieNode();


            if (!temp.containsKey(s.charAt(i))){
                temp.put(s.charAt(i) + "", root);
            }
            if (i == s.length() - 1){
                temp.get(s.charAt(i)).isWord = true;
            }
            temp = temp.get(s.charAt(i)).children;

        }
    }
    private TrieNode searchNode(String s){
        TrieNode temp = this;
        for (int i = 0; i < s.length(); i++){
            if (!temp.children.containsKey(s.charAt(i))){
                return null;
            }
            temp = temp.children.get(s.charAt(i));
        }
        return temp;
    }
    public boolean isWord(String s) {

        TrieNode temp = searchNode(s);
        if (temp == null)
            return false;
        else
            return temp.isWord;
    }

    public String getAnyWordStartingWith(String s) {
        TrieNode temp = searchNode(s);
        if (temp == null){
            return null;
        }
        while (!temp.isWord){
            for (String c: temp.children.keySet()){
                temp = temp.children.get(c);
                s += c;
                break;
            }
        }
        return s;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
