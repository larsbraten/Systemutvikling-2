package v5.gidd.entities.activity.filter;

import org.springframework.data.domain.Sort;

/**
 * Provides options for fetching activities, such as sorting the return value.
 */

public class ActivityPage {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "startTime";


    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public String toString() {
        return "ActivityPage{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", sortDirection=" + sortDirection +
                ", sortBy='" + sortBy + '\'' +
                '}';
    }
}
