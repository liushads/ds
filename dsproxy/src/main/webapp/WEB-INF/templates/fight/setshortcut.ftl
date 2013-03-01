<#include "/include/header.ftl">
<card title="${game_title}"><p>

[药品]快捷栏:<br/>
<#if shortcut?exists>
<#list shortcut as ls>
<@a href="/p?p_SSC/${pid}/yp/${ls[0].id}/"/>卸下</a>${ls[0].name}<br/>
</#list>
<#else>无<br/>
</#if>
<#--
[绝学]快捷栏:<br/>
<#if curr_jx?exists>
<@a href="/p?p_SSC/${pid}/jx/${curr_jx.id}/"/>卸下</a>${curr_jx.name}<br/>
<#else>无<br/>
</#if>
-->
[暗器]快捷栏:<br/>
<#if curr_aq?exists>
<@a href="/p?p_SSC/${pid}/aq/${curr_aq.id}/"/>卸下</a>${curr_aq.name}<br/>
<#else>无<br/>
</#if>

<@goback/>
<@gogame/>
</p>
</card>
</wml>