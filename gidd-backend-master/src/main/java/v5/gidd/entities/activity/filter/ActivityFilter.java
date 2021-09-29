package v5.gidd.entities.activity.filter;

import v5.gidd.entities.Inspectable;

/**
 * Filter providing search criteria for various activities. Can be used to fetch one or many activities
 * backend-side.
 */

public class ActivityFilter implements Inspectable {

    private ActivitySearchCriteria activitySearchCriteria;
    private ActivityPage activityPage;

    public ActivityFilter(ActivitySearchCriteria activitySearchCriteria, ActivityPage activityPage) {
        this.activitySearchCriteria = activitySearchCriteria;
        this.activityPage = activityPage;
    }

    public ActivityFilter() {
    }

    public ActivitySearchCriteria getActivitySearchCriteria() {
        return activitySearchCriteria;
    }

    public void setActivitySearchCriteria(ActivitySearchCriteria activitySearchCriteria) {
        this.activitySearchCriteria = activitySearchCriteria;
    }

    public ActivityPage getActivityPage() {
        return activityPage;
    }

    public void setActivityPage(ActivityPage activityPage) {
        this.activityPage = activityPage;
    }

    @Override
    public void inspect() {
    }

    @Override
    public String toString() {
        return "ActivityFilter{" +
                "activitySearchCriteria=" + activitySearchCriteria +
                ", activityPage=" + activityPage +
                '}';
    }
}
