<#include "/include/header.ftl">
<card title="${game_title}">
<p>
请输入要取出的数量<br/>
<input type="text" name="num" value="${number}" format="*N" maxlength="5"></input><br/>
<anchor>确定
	<@go href="/p?n_sr/${pid}/" method="post"/>
        <postfield name="1" value="takeout" />
        <postfield name="2" value="${stid}" />
        <postfield name="3" value="${type}" />
        <postfield name="4" value="$num" />
    </go>
</anchor>
<@goback/>
<@gofacility/>
</p>
</card>
</wml>


 