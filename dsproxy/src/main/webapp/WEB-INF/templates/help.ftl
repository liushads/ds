<#include "/include/header.ftl">
<card title="${game_title}"><p>


<#if qtype?exists>
<@a href="/p?p_HL/${pid}/t/"/>游戏帮助</a>>${qtype.description}
<#else>
游戏帮助
</#if>

<br/>

<#if qas?exists>
<#if (qas?size>0)>
	<#list qas as qa>		
		${qa_index + 1}. <@a href="/p?p_HL/${pid}/q/${qa.id}/"/>${qa.question}</a><br/>
	</#list>
</#if>
</#if>

<#if qa?exists>
问：${qa.question}<br/>
答：${qa.answer}<br/>
</#if>

<#if qtypes?exists>
<#if (qtypes?size>0)>
	<#list qtypes as qtype>		
		${qtype.type}. <@a href="/p?p_HL/${pid}/l/${qtype.type}/"/>${qtype.description}</a><br/>
	</#list>
	<input name="keyword" type="text" emptyok="true" />
	
  <anchor>
        搜索
   <@go href="/p?p_HL/${pid}/" method="post"/>
	<postfield name="1" value="s" />
	<postfield name="2" value="$keyword" />
   </go>
  </anchor>
  <br/>
</#if>
</#if>

<@goback />
<@gogame />
</p></card>
</wml>