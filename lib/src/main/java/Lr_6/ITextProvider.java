package Lr_6;

/**
 * 
 */
public interface ITextProvider {

    /**
     * 
     * @return
     */
    public String generateText();

    /**
     * 
     * @param cnt
     */
    public void setCntWords(int cnt);

    /**
     * 
     * @param mode
     */
    public void setMode(String mode);

    /**
     * 
     * @return
     */
    public String[] getAvailableModes();
}
