package Lr_5;

public class OperationStats {
    private String collectionType;
    private int size;
    private String operationType;
    private int countItem;
    private long totalTime;
    private long medianTime;

    /**
     * 
     * @param collectionType
     * @param size
     * @param operationType
     * @param countItem
     * @param totalTime
     * @param medianTime
     */
    public OperationStats(String collectionType, int size, String operationType, int countItem, long totalTime,
	    long medianTime) {
	this.collectionType = collectionType;
	this.size = size;
	this.operationType = operationType;
	this.countItem = countItem;
	this.totalTime = totalTime;
	this.medianTime = medianTime;
    }

    /**
     * 
     * @return
     */
    public String getCollectionType() {
	return collectionType;
    }

    /**
     * 
     * @return
     */
    public int getSize() {
	return size;
    }

    /**
     * 
     * @return
     */
    public String getOperationType() {
	return operationType;
    }

    /**
     * 
     * @return
     */
    public int getCountItem() {
	return countItem;
    }

    /**
     * 
     * @return
     */
    public long getTotalTime() {
	return totalTime;
    }

    /**
     * 
     * @return
     */
    public long getMedianTime() {
	return medianTime;
    }

    @Override
    public String toString() {
	return "OperationStats{" + "collectionType='" + collectionType + '\'' + ", size=" + size + ", operationType='"
		+ operationType + '\'' + ", countItem=" + countItem + ", totalTime=" + totalTime + ", medianTime="
		+ medianTime + '}';
    }
}
