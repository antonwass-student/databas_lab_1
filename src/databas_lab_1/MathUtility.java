/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

/**
 *
 * @author Anton
 */
public abstract class MathUtility {
    public static float clamp(float value, float min, float max){
        Math.min(Math.max(value, max), min);
        return value;
    }
    
    public static float clamp(int value, int min, int max){
        Math.min(Math.max(value, max), min);
        return value;
    }
}
