<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
你有押镖任务未完成，不能传送，确定要丢弃所有押镖任务后再传送吗？<br/>
<@a href="/p?mv_TrD/${pid}/${cityId}/${payType}"/>确定</a>
<br/>
<@gofacility/>
</p></card>
</wml>
