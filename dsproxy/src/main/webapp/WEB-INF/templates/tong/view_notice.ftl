<#include "/include/header.ftl">
<card title="${tong_notice.title?if_exists}"><p>
 
<#if tong_notice?exists>
${tong_notice.content}<br/>
${tong_notice.addTime?string("yyyy-MM-dd")}
</#if>
<br/>
<@a href="/p?t_NL/${pid}"/>查看全部</a><br/>
<@a href="/p?t_M/${pid}/"/>返回帮会</a><br/>
<@gogame/>
</p>  
</card>  
</wml> 