<#include "/include/header.ftl">
<card title="${game_title}"><p>
快问乱答<br/>
${question.description}<br/>
<#if questionlist?exists>
<#list questionlist as da>
<#if da_index==0>
 <@a href="/p?n_lan/${pid}/dati/${question.id}/0"/>A.${da}</a><br/>
</#if>
<#if da_index==1>
 <@a href="/p?n_lan/${pid}/dati/${question.id}/1"/>B.${da}</a><br/>
</#if>
<#if da_index==2>
 <@a href="/p?n_lan/${pid}/dati/${question.id}/2"/>C.${da}</a><br/>
</#if>
<#if da_index==3>
 <@a href="/p?n_lan/${pid}/dati/${question.id}/3"/>D.${da}</a><br/>
</#if>
</#list>
</#if>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>