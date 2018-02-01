package model;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    public List<List<String>> matrix;
    public List<String> markets;
    public List<String> assort;

    public Matrix(){
        markets = new ArrayList<String>();
        assort = new ArrayList<String>();
        matrix = new ArrayList<List<String>>();
    }
}
