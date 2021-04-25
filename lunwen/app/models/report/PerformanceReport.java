package models.report;

public class PerformanceReport extends BaseReport {

    /** 颗粒  */
    public String modelName;
    /** KPI类别  */
    public String businessSys;
    /** 服务器名称或IP */
    public String ipAddress;
    /** 最大值 */
    public String max;
    /** 平均值 */
    public String avg;
    /** 发生时间 */
    public String hapDate;
}
