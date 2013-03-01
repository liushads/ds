<#include "/include/header.ftl">
<card title="${game_title}"><p>
提示:<#if desc?exists>${desc}</#if>
<br/>
<#if redirect?exists>
<@a href="/p?${redirect.ctnueCmd}/${pid}/${redirect.param}"/>确定</a><br/>
</#if>
<@goback/>
<@gogame/>
</p>
</card>
</wml>