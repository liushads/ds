<#include "/include/header.ftl">
<card title="${game_title}"><p>
扫墓的百姓:<br/>
每集齐：清明菊花8个 清明糯米8个 清明豆沙8个 清明白玫瑰8个 清明核桃8个 还可以兑换1张答题卷、每12个小时只能兑换1张答题卷<br/>
<#if td?exists>
${td}
</#if>

<@gofacility/>
<@gogame/>
</p></card>
</wml>