package com.caterpillar.shamil.LibraryPet.comparators;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 07.05.2023
 */
public class CompareCharArray {
    public static int compareCharArray(char[] arr1, char[] arr2){
        var end = Math.min(arr1.length, arr2.length);
        for (int i = 0; i < end; i++){
            if ((arr1[i] - arr2[i]) != 0)
                return (arr1[i] - arr2[i]);// / Math.abs(arr1[i] - arr2[i]);
        }
        return arr1.length-arr2.length;// / Math.abs(arr1.length - arr2.length);
    }
}
