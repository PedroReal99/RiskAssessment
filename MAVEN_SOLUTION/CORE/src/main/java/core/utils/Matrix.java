/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.utils;

import core.domain.RiskMatrixs.Columns.ColumnBuilder;
import eapli.framework.validations.Preconditions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.util.Pair;

/**
 *
 * @author CarloS
 * @param <L> Lines' element class
 * @param <C> Columns' element class
 * @param <Y> Intersection's element class
 */
public class Matrix<L, C, Y> {

    private int numE;

    /**
     * All the elements of the lines, ordered by insertion
     * This duplication exists in order to ease the listing process
     */
    private Set<L> lines;
    
    /**
     * All the elements of the columns, ordered by insertion
     * This duplication exists in order to ease the listing process
     */
    private Set<C> columns;
    
    /**
     * Matrix
     */
    private Map<Pair<L, C>, Y> matrix;

    /**
     * Constructor of matrix
     */
    public Matrix() {
        numE = 0;
        lines=new LinkedHashSet<>();
        columns= new LinkedHashSet<>();
        matrix = new HashMap<>();
    }

    /**
     * Sets an elemnt to the matrix, or replaces, in case it is empty
     *
     * @param line
     * @param column
     * @param element
     * @return
     */
    public Y set(L line, C column, Y element) {
        Preconditions.nonNull(line, column);
        
        lines.add(line);
        columns.add(column);
        
        Y y = matrix.put(new Pair<>(line, column), element);
        if (y == null) {
            numE++;
        }
        return y;
    }

    /**
     * Finds and returns the element Y of the cell represented by the line and column
     * @param line      line
     * @param column    column
     * @return          the element, if found, else null
     */
    public Y get(L line, C column) {
        Preconditions.nonNull(line, column);
        return matrix.get(new Pair<>(line, column));
    }

    /**
     * Returns all the elements of a line
     * @param line line
     * @return 
     */
    public List<Y> getLine(L line) {
        List<Y> list=new ArrayList<>();
        Y elm;
        for(C c:columns){
            if((elm=(matrix.get(new Pair<>(line,c))))!=null){
                list.add(elm);
            }
        }
        return list;
    }
        
    /**
     * returns all line elements
     * @return 
     */
    public Set<L> getLines() {
        return new LinkedHashSet<>(lines);
    }
    
    /**
     * Returns all column elements
     * @return 
     */
    public Set<C> getColumns(){
        return new LinkedHashSet<>(columns);
    }
    
    /**
     * Number of elements
     * @return 
     */
    public int getNumE() {
        return numE;
    }

    /**
     * Removes an element from the matrix
     * @param line      line of the element
     * @param column    column of the element
     * @return  the element, if it exists, or null 
     */
    public Y remove(L line, C column) {
        Preconditions.nonNull(line, column);
        Y y = matrix.remove(new Pair(line, column));
        if (y != null) {
            numE--;
        }
        return y;
    }

}
