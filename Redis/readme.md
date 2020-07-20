## Redis

### Nosql

#### 概念

​	`Not Only SQL`

​     泛指非关系型的数据库。随着互联网web2.0网站的兴起，传统的关系数据库在应付web2.0网站，特别是超大规模和高并发的SNS类型的web2.0纯动态网站已经显得力不从心，暴露了很多难以克服的问题，而非关系型的数据库则由于其本身的特点得到了非常迅速的发展。**NoSQL数据库的产生就是为了解决大规模数据集合多重数据种类带来的挑战，尤其是大数据应用难题，包括超大规模数据的存储。**

#### 为什么要用

​      用户的个人信息，社交网络，地理位置，用户生成的数据和用户操作日志已经成倍的增加。我们如果要对这些用户数据进行挖掘，那`SQL`数据库已经不适合这些应用了, `NoSQL`数据库的发展也却能很好的处理这些大的数据

#### 特点

- 方便扩展

  NoSQL数据库种类繁多，但是一个共同的特点都是去掉关系数据库的关系型特性。
  数据之间无关系，这样就非常容易扩展。也无形之间，在架构的层面上带来了可扩展的能力。

- 大数据量高性能（Redis一秒写8万次，读11万次）

- 数据类型多样

- 传统的RDBMS VS NoSQL

  ```java
  RDBMS vs NoSQL
   
  RDBMS
  - 高度组织化结构化数据
  - 结构化查询语言（SQL）
  - 数据和关系都存储在单独的表中。
  - 数据操纵语言，数据定义语言
  - 严格的一致性
  - 基础事务
   
  NoSQL
  - 代表着不仅仅是SQL
  - 没有声明性查询语言
  - 没有预定义的模式
  - 键 - 值对存储，列存储，文档存储，图形数据库
  - 最终一致性，而非ACID属性
  - 非结构化和不可预知的数据
  - CAP定理
  - 高性能，高可用性和可伸缩性
  ```

#### 分类

- `KV` 键值对：`Redis`
- 文档型数据库：`mongoDB`
- 列存储：`HBase`
- 图关系型数据库





```html
<h1 style="text-align:center">ECM电源故障</h1>

<p><strong>【故障代码说明】</strong>&nbsp; &nbsp;</p>

<table border="1" cellpadding="1" cellspacing="0">
	<tbody>
		<tr>
			<th>&nbsp;</th>
			<th>DTC</th>
			<th>故障说明</th>
			<th>DTC触发条件</th>
			<th>DTC检测条件（控制策略）</th>
			<th>故障部位</th>
		</tr>
		<tr>
			<td>
			<p>1</p>
			</td>
			<td>U350200</td>
			<td>传感器内部元件损坏</td>
			<td>硬件电路检查</td>
			<td>驻车，启动</td>
			<td>
			<p>1.传感器</p>

			<p>2.线路</p>

			<p>&nbsp;</p>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</tbody>
</table>

<p>进气压力传感器响应进气歧管内的压力变化。压力根据发动机负荷而变化。</p>

<p>ECM通过ECM线束连接器EN72的33号端子给传感器线速连接器EN77的3号端子提供5V参考电压。同时还通过EN72的17号端子给传感器EN77的1号脚提供一个低参考电压电路。传感器通过信号电路EN77的4号端子向ECM线束连接器EN72的37号端子提供一个信号，该信号与进气歧管中的压力变化相关。在进气歧管绝对压力较低时ECM检测到的信号电压应较低，比如在怠速或减速期间。当进气歧管绝对压力较高时，ECM检测到的信号电压应较高，比如在点火开关接通而发动机关闭时，或在节气门全开时。传感器还被用来确定大气压力。当点火开关接通而发动机关闭时会出现此情形。只要在节气门全开的情况下运行发动机，大气压力读数也会更新。ECM监测传感器信号，以确定电压是否超出正常范围。</p>

<p>2、故障代码设置及故障部位</p>

<table>
	<tbody>
		<tr>
			<td>
			<p>DTC编号</p>
			</td>
			<td>
			<p>DTC检测策略</p>
			</td>
			<td>
			<p>DTC设置条件(控制策略)</p>
			</td>
			<td>
			<p>故障部位</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>P0105</p>
			</td>
			<td>
			<p>信号检查：启动后压力没有降低</p>
			</td>
			<td>
			<p>1、启动后的压力与初始压力的压力降小于2kPa。</p>

			<p>2、发动机转速大于800rpm</p>
			</td>
			<td rowspan="4">
			<p>1、传感器电路。</p>

			<p>2、传感器。</p>

			<p>3、ECM。</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>P0106</p>
			</td>
			<td>
			<p>信号不合理</p>
			</td>
			<td>
			<p>压力传感器显示的压力</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>P0107</p>
			</td>
			<td>
			<p>电路检查，超出下限</p>
			</td>
			<td>
			<p>压力传感器电压小于0.1V</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>P0108</p>
			</td>
			<td>
			<p>电路检查，超出上限</p>
			</td>
			<td>
			<p>压力传感器电压大于4.9V</p>
			</td>
		</tr>
	</tbody>
</table>

<p>3、电路简图</p>

<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/23800013021498d.svg" style="height:454px; width:677px" /></p>

<p>4、诊断步骤</p>

<table>
	<tbody>
		<tr>
			<td colspan="3">
			<p>在执行本诊断步骤之前，观察故障诊断仪的数据列表，分析各项数据的准确性，这样有助于快速排除故障。</p>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<table>
				<tbody>
					<tr>
						<td>
						<p>步骤1</p>
						</td>
						<td>
						<p>初步检查</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="2">
			<p>检查是否存在以下状况：</p>

			<p>传感器外壳损坏、真空管破裂</p>

			<p>传感器密封件损坏</p>

			<p>传感器松动或安装不正确</p>

			<p>传感器真空管堵塞</p>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000109d.svg" style="height:18px; width:52px" /></p>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<table>
				<tbody>
					<tr>
						<td>
						<p>步骤2</p>
						</td>
						<td>
						<p>测量进气歧管绝对压力传感器5V参考电压</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td rowspan="2">
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/23800013021499d.svg" style="height:246px; width:321px" /></p>
			</td>
			<td colspan="2">
			<p>严禁进气歧管绝对压力传感器的5V参考电压电路与车辆的其它部件相连，否则会损坏传感器及ECM！</p>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>转动点火开关至&quot;OFF&quot;位置</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>断开进气歧管绝对压力传感器线束连接器EN77。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>转动点火开关至&quot;ON&quot;位置。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>测量进气歧管绝对压力传感器线束连接器EN77的3号端子与可靠接地之间的电压。
						<p>标准电压值：4.5V～5.5V</p>
						</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>连接进气岐管绝对压力传感器线束连接器EN77。</td>
					</tr>
				</tbody>
			</table>

			<p>电压是否符合规定值？</p>
			</td>
		</tr>
		<tr>
			<td>
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000117d.svg" style="height:18px; width:52px" /></p>
			</td>
			<td>
			<table>
				<tbody>
					<tr>
						<td>
						<p>转至步骤6</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000108d.svg" style="height:18px; width:52px" /></p>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<table>
				<tbody>
					<tr>
						<td>
						<p>步骤3</p>
						</td>
						<td>
						<p>测量传感器信号电路</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td rowspan="2">&nbsp;</td>
			<td colspan="2">
			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>转动点火开关至&quot;OFF&quot;位置。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>断开进气歧管绝对压力传感器线束连接器EN77。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>转动点火开关至&quot;ON&quot;位置。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>在EN77的1号和4号端子之间连接一根带5A保险丝的跨接线，用故障诊断仪观察&quot;实际进气歧管绝对压力传感器电压&quot;参数。
						<p>标准参数：4.5V～5.5V</p>
						</td>
					</tr>
				</tbody>
			</table>

			<p>连接进气岐管绝对压力传感器线束连接器EN77。</p>

			<p>数据正常吗？</p>
			</td>
		</tr>
		<tr>
			<td>
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000117d.svg" style="height:18px; width:52px" /></p>
			</td>
			<td>
			<table>
				<tbody>
					<tr>
						<td>
						<p>转至步骤7</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000108d.svg" style="height:18px; width:52px" /></p>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<table>
				<tbody>
					<tr>
						<td>
						<p>步骤4</p>
						</td>
						<td>
						<p>测量进气歧管绝对压力传感器接地电路</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td rowspan="2">&nbsp;</td>
			<td colspan="2">
			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>转动点火开关至&quot;OFF&quot;位置。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>断开进气歧管绝对压力传感器线束连接器EN77。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>转动点火开关至&quot;ON&quot;位置。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>测量进气歧管绝对压力传感器线束连接器EN77的1号端子与可靠接地之间的电阻。
						<p>标准值：小于3&Omega;</p>
						</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>连接进气岐管绝对压力传感器线束连接器EN77。</td>
					</tr>
				</tbody>
			</table>

			<p>电阻值正常吗？</p>
			</td>
		</tr>
		<tr>
			<td>
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000117d.svg" style="height:18px; width:52px" /></p>
			</td>
			<td>
			<table>
				<tbody>
					<tr>
						<td>
						<p>转至步骤8</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000108d.svg" style="height:18px; width:52px" /></p>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<table>
				<tbody>
					<tr>
						<td>
						<p>步骤5</p>
						</td>
						<td>
						<p>更换进气岐管绝对压力传感器</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000119d.svg" style="height:18px; width:52px" /></p>
			</td>
			<td>
			<table>
				<tbody>
					<tr>
						<td>
						<p>转至步骤10</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<table>
				<tbody>
					<tr>
						<td>
						<p>步骤6</p>
						</td>
						<td>
						<p>检查传感器5V参考电压电路</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td rowspan="2">
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/23800013021500d.svg" style="height:246px; width:321px" /></p>
			</td>
			<td colspan="2">
			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>转动点火开关至&quot;OFF&quot;位置。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>断开进气歧管绝对压力传感器线束连接器EN77。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>断开ECM线束连接器EN72。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>测量进气歧管绝对压力传感器线束连接器EN77的3号端子与ECM线束连接器19号端子之间的电阻值，检查是否存在断路情况，否则修理故障部位。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>测量进气歧管绝对压力传感器线束连接器EN77的3号端子与可靠接地之间的电阻值，检查是否存在对地短路情况，否则修理故障部位。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>测量进气歧管绝对压力传感器线束连接器EN77的3号端子与可靠接地之间的电压值，检查是否存在对电源短路情况，否则修理故障部位。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>
						<p>测量项目</p>
						</td>
						<td>
						<p>标准值</p>
						</td>
					</tr>
					<tr>
						<td>
						<p>EN77（3）-EN72（33）电阻值</p>
						</td>
						<td>
						<p>小于1&Omega;</p>
						</td>
					</tr>
					<tr>
						<td>
						<p>EN77（3）-可靠接地电阻值</p>
						</td>
						<td>
						<p>10k&Omega;或更高</p>
						</td>
					</tr>
					<tr>
						<td>
						<p>EN77（3）-可靠接地电压值</p>
						</td>
						<td>
						<p>0V</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000119d.svg" style="height:18px; width:52px" /></p>
			</td>
			<td>
			<table>
				<tbody>
					<tr>
						<td>
						<p>转至步骤9</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<table>
				<tbody>
					<tr>
						<td>
						<p>步骤7</p>
						</td>
						<td>
						<p>检查传感器信号电路</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td rowspan="2">
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/23800013021501d.svg" style="height:246px; width:321px" /></p>
			</td>
			<td colspan="2">
			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>转动点火开关至&quot;OFF&quot;位置。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>断开进气歧管绝对压力传感器线束连接器EN77。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>断开ECM线束连接器EN72。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>测量进气歧管绝对压力传感器线束连接器EN77的4号端子与ECM线束连接器37号端子之间的电阻值，检查是否存在断路情况，否则修理故障部位。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>测量进气歧管绝对压力传感器线束连接器EN77的4号端子与可靠接地之间的电阻值，检查是否存在对地短路情况，否则修理故障部位。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>测量进气歧管绝对压力传感器线束连接器EN77的4号端子与可靠接地之间的电压值，检查是否存在对电源短路情况，否则修理故障部位。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>
						<p>测量项目</p>
						</td>
						<td>
						<p>标准值</p>
						</td>
					</tr>
					<tr>
						<td>
						<p>EN77（4）-EN72（37）电阻值</p>
						</td>
						<td>
						<p>小于1&Omega;</p>
						</td>
					</tr>
					<tr>
						<td>
						<p>EN77（4）-可靠接地电阻值</p>
						</td>
						<td>
						<p>10k&Omega;或更高</p>
						</td>
					</tr>
					<tr>
						<td>
						<p>EN77（4）-可靠接地电压值</p>
						</td>
						<td>
						<p>0V</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000121d.svg" style="height:18px; width:52px" /></p>
			</td>
			<td>
			<table>
				<tbody>
					<tr>
						<td>
						<p>转至步骤9</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<table>
				<tbody>
					<tr>
						<td>
						<p>步骤8</p>
						</td>
						<td>
						<p>检查传感器接地电路</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="2">
			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>转动点火开关至&quot;OFF&quot;位置。B</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>断开进气歧管绝对压力传感器线束连接器EN77。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>断开ECM线束连接器EN72。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>测量进气歧管绝对压力传感器线束连接器EN77的1号端子与ECM线束连接器17号端子之间的电阻值，检查是否存在断路情况，否则修理故障部位。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>测量进气歧管绝对压力传感器线束连接器EN77的1号端子与可靠接地之间的电压值，检查是否存在对电源短路情况，否则修理故障部位。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>
						<p>测量项目</p>
						</td>
						<td>
						<p>标准值</p>
						</td>
					</tr>
					<tr>
						<td>
						<p>EN77（1）-EN72（17）电阻值</p>
						</td>
						<td>
						<p>小于1&Omega;</p>
						</td>
					</tr>
					<tr>
						<td>
						<p>EN77（1）-可靠接地电压值</p>
						</td>
						<td>
						<p>0V</p>
						</td>
					</tr>
				</tbody>
			</table>

			<p>正常执行下一步</p>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000109d.svg" style="height:18px; width:52px" /></p>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<table>
				<tbody>
					<tr>
						<td>
						<p>步骤9</p>
						</td>
						<td>
						<p>检查ECM电源电路</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td rowspan="2">&nbsp;</td>
			<td colspan="2">
			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>检查ECM电源电路是否正常。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>检查ECM接地电路是否正常</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000117d.svg" style="height:18px; width:52px" /></p>
			</td>
			<td>
			<table>
				<tbody>
					<tr>
						<td>
						<p>处理故障部位</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000108d.svg" style="height:18px; width:52px" /></p>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<table>
				<tbody>
					<tr>
						<td>
						<p>步骤10</p>
						</td>
						<td>
						<p>更换ECM</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000109d.svg" style="height:18px; width:52px" /></p>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<table>
				<tbody>
					<tr>
						<td>
						<p>步骤11</p>
						</td>
						<td>
						<p>利用故障诊断仪确认故障代码是否再次存储</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td rowspan="2">&nbsp;</td>
			<td colspan="2">
			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>连接故障诊断仪至诊断测试接口。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>转动点火开关至&quot;ON&quot;位置。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>清除故障诊代码。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>启动发动机并怠速暖机运行至少5min。</td>
					</tr>
				</tbody>
			</table>

			<table>
				<tbody>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>再次对控制系统进行故障代码读取，确认系统无故障代码输出。</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000117d.svg" style="height:18px; width:52px" /></p>
			</td>
			<td>
			<table>
				<tbody>
					<tr>
						<td>
						<p>间歇性故障，参见&quot;1.1.7.3间歇性故障的检查&quot;</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<p><img alt="Graphic" src="http://gtis.geely.com/imeWeb/lucene/FC01_zh/graphics/00000000000108d.svg" style="height:18px; width:52px" /></p>
			</td>
		</tr>
		<tr>
			<td colspan="3">
			<table>
				<tbody>
					<tr>
						<td>
						<p>步骤12</p>
						</td>
						<td>
						<p>故障排除</p>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
</table>

<p>5、维修指南</p>

<p>更换MAP传感器参见&quot;1.1.8.7进气压力温度传感器的更换&quot;。更换ECM参见&quot;1.1.8.8发动机控制模块的更换&quot;</p>
```



### 测试性能

#### Redis-benchmark
测试100个并发连接，10000个请求

```java
redis-benchmark -h localhost -p 6379 -c 100 -n 10000
```



###  基本知识

 默认有 **16** 个数据库，默认使用第 **0** 个数据库

```shell
[root@iz2ze0obn92kpmakmtjw73z redis]# docker exec -it master-redis redis-cli
127.0.0.1:6379> auth root
OK
127.0.0.1:6379> select 3   # 选择3号数据库
OK
127.0.0.1:6379[3]> dbsize 
(integer) 0
127.0.0.1:6379[3]> flushdb  # 清空当前库
OK
127.0.0.1:6379> FLUSHALL   #清空所有数据库
OK
127.0.0.1:6379> keys *   # 查看所有键
127.0.0.1:6379> exists key   # 判断key是否存在
127.0.0.1:6379> expire key seconds  # 设置过期时间
127.0.0.1:6379> ttl key    # 查看当前key的剩余时间
127.0.0.1:6379> type key   # 查看当前key的类型
127.0.0.1:6379> del key   # 删除指定key
```

> Redis 是单线程的

​	redis是基于内存操作的，CPU不是Redis的性能瓶颈，Redis的性能瓶颈是根据机器的内存和网络带宽，既然可以使用单线程来实现，就使用单线程了。

​	redis是C语言写的，官方提供的数据尾100000+的QPS，

 **redis 为什么单线程还这么快**

1、误区一：高性能的服务器一定是多线程的

2、误区二：多线程（CPU上下文会切换）一定比单线程快

CPU>内存>硬盘的速度

核心：redis是将所有的数据全部放在内存中的，所以说使用单线程去操作效率就是最高的，多线程（CPU上下文会切换：是一个耗时的操作），对于内存系统来说，如果没有上下文切换效率就是最高的。多次读写都是在一个CPU上的，在内存情况下，这个是最佳的方案。



### 五大基本数据类型

#### String

```java
127.0.0.1:6379> strlen name  // 获取key的长度
(integer) 3
127.0.0.1:6379> set key1 'aaa'
OK
127.0.0.1:6379> APPEND key1 'bbb'  // 在key的后面追加
(integer) 6
127.0.0.1:6379> get key1
"aaabbb"
127.0.0.1:6379> set view 1
OK
127.0.0.1:6379> incr view  // 自增1
(integer) 2
127.0.0.1:6379> get view
"2"
127.0.0.1:6379> incr view
(integer) 3
127.0.0.1:6379> get view
"3"
127.0.0.1:6379> incrby view 5  // 指定key加上数值
(integer) 8
127.0.0.1:6379> get view
"8"
127.0.0.1:6379> decr view   // 减一
(integer) 7
127.0.0.1:6379> decrby view 2  // 指定key减去数值
(integer) 5
127.0.0.1:6379> get view
"5"
127.0.0.1:6379> GETRANGE key1 0 3   // 截取字符串，前后都是闭合区间
"aaab"
127.0.0.1:6379> setrange key1 3 c  // 替换指定位置开始的字符串
(integer) 6
127.0.0.1:6379> get key1
"aaacbb"
127.0.0.1:6379> SETEX key2 50 'hello'   // 设置key的过期时间
OK
127.0.0.1:6379> ttl key2
(integer) 48
127.0.0.1:6379> get key2
"hello"
127.0.0.1:6379> setnx key3 'redis'    // 如果key不存在，创建
(integer) 1
127.0.0.1:6379> keys *
1) "key1"
2) "key3"
3) "name"
4) "view"
5) "key2"
127.0.0.1:6379> get keys3
(nil)
127.0.0.1:6379> setnx key3 'MongoDB'   // 如果key存在，创建失败
(integer) 0
127.0.0.1:6379> get key3
"redis"
    
127.0.0.1:6379> mset k1 v1 k2 v2 k3 v3  // 同时设置多个值
OK
127.0.0.1:6379> keys *
1) "k1"
2) "k3"
3) "k2"
127.0.0.1:6379> mget k1 k2 k3    // 同时获取多个值
1) "v1"
2) "v2"
3) "v3"
127.0.0.1:6379> msetnx k1 v1 k4 v4   // msetnx是一个原子性的操作，要么一起成功，要么一起失败
(integer) 0
127.0.0.1:6379> keys *
1) "k1"
2) "k3"
3) "k2"
    
// 对象
127.0.0.1:6379>set user:1 json字符串  // 设置某个用户的信     息 
```

**总结**

String类型的使用场景：value除了是字符串还可以是数字

- 计数器
- 统计多单位的数量
- 粉丝数
- 对象缓存存储



#### List

所有的List命令都是以 **l** 开头的

```shell
127.0.0.1:6379> lpush list one
(integer) 1
127.0.0.1:6379> lpush list two
(integer) 2
127.0.0.1:6379> lpush list three
(integer) 3
127.0.0.1:6379> lrange list 0 -1   // 查看key的数据
1) "three"
2) "two"
3) "one"
127.0.0.1:6379> lrange list 0 1   //通过区间获取具体的值
1) "three"
2) "two"
127.0.0.1:6379> rpush list rightr  // 从右边插入
(integer) 4
127.0.0.1:6379> lrange list 0 -1
1) "three"
2) "two"
3) "one"
4) "rightr"
127.0.0.1:6379> lpop list    // 左边弹出
"three"
127.0.0.1:6379> rpop list    // 右边弹出
"rightr"
    
127.0.0.1:6379> lindex list 0   // 根据索引查询
"two"
127.0.0.1:6379> rindex list 0   // 无此命令
(error) ERR unknown command `rindex`, with args beginning with: `list`, `0`, 
127.0.0.1:6379> llen list   //查询长度
(integer) 2
    
    
127.0.0.1:6379> lpush list five
(integer) 3
127.0.0.1:6379> lrange list 0 -1
1) "five"
2) "two"
3) "one"
127.0.0.1:6379> lpush list five
(integer) 4
127.0.0.1:6379> lpush list five
(integer) 5
127.0.0.1:6379> lrem list 1 five
(integer) 1
127.0.0.1:6379> lrange list 0 -1
1) "five"
2) "five"
3) "two"
4) "one"
127.0.0.1:6379> lrem list 2 five  // 移除指定key指定个数
(integer) 2
127.0.0.1:6379> lrange list 0 -1
1) "two"
2) "one"
127.0.0.1:6379> ltrim list 1 2  // 通过下标截取列表
127.0.0.1:6379> lset list 1 'hello' // 更新指定下标的值，如果下标不存在，会报错

127.0.0.1:6379> lset insert before/after key1 key2 // 将某个具体的value值插入到列的某个元素的前面或者后面
```

**总结**

- 实际上就是一个链表，before node after ,left, right 都可以插入
- 如果key不存在，创建新链表
- 如果key存在，新增内容
- 如果移除了所有值，空链表，也代表不存在
- 在两边插入或者改动值，效率最高，中间元素，相对效率会低
- 可用在消息排队，消息队列，栈

#### Set

set 无序不重复

```shell
127.0.0.1:6379> sadd myset 'hello'   # set集合添加元素
(integer) 1
127.0.0.1:6379> sadd myset 'kxj'
(integer) 1
127.0.0.1:6379> sadd myset 'java'
(integer) 1
127.0.0.1:6379> SMEMBERS myset  # 查看指定set的所有值
1) "java"
2) "hello"
3) "kxj"
127.0.0.1:6379> sismember myset java   # 判断某一个值是否在集合中
(integer) 1
127.0.0.1:6379> sismember myset php
(integer) 0

127.0.0.1:6379> scard myset    # 获取集合个数
(integer) 3
127.0.0.1:6379> sadd myset 'java'  # 不允许重复
(integer) 0

127.0.0.1:6379> srem myset hello  # 移除指定元素
(integer) 1

# 随机抽选一个元素
127.0.0.1:6379> SRANDMEMBER myset
"kxj"
127.0.0.1:6379> SRANDMEMBER myset
"java"
# 随机抽选指定个数元素
127.0.0.1:6379> SRANDMEMBER myset 2
"kxj"
"java"

127.0.0.1:6379> SPOP myset  # 随机移除元素
"java"
127.0.0.1:6379> SMEMBERS myset
1) "php"
2) "kxj"
3) "python"

127.0.0.1:6379> sadd myset1 'php'
(integer) 1
127.0.0.1:6379> sadd myset1 'java'
(integer) 1
127.0.0.1:6379> SDIFF myset myset1   # 差集
1) "kxj"
2) "python"
127.0.0.1:6379> SINTER myset myset1 # 交集，共同好友
1) "php"
127.0.0.1:6379> SUNION myset myset1 # 并集
1) "java"
2) "php"
3) "kxj"
4) "python"
```

#### hash

map集合，key-value

```shell
127.0.0.1:6379> hset myhash name kxj  # 设置key-value
(integer) 1
127.0.0.1:6379> hget myhash name    
"kxj"
127.0.0.1:6379> hmset myhash age 18 sex man  # 设置多个key-value 
(integer) 2
127.0.0.1:6379> hmget myhash name age sex  # 获取多个字段值
1) "kxj"
2) "18"
3) "man"
127.0.0.1:6379> HGETALL myhash # 获取所有
1) "name"
2) "kxj"
3) "age"
4) "18"
5) "sex"
6) "man"
127.0.0.1:6379> hdel myhash sex  # 删除指定键
(integer) 1
127.0.0.1:6379> HGETALL myhash
1) "name"
2) "kxj"
3) "age"
4) "18"


127.0.0.1:6379> hmset myhash  sex man work developer
OK
127.0.0.1:6379> hlen myhash  # 长度
(integer) 4
127.0.0.1:6379> HEXISTS myhash age  # 判断是否存在
(integer) 1
127.0.0.1:6379> HKEYS myhash  # 获取所有的key
1) "name"
2) "age"
3) "sex"
4) "work"
127.0.0.1:6379> HVALS myhash # 获取所有的value
1) "kxj"
2) "18"
3) "man"
4) "developer"
```

hash变更的数据 user中name,age，尤其是用户信息之类的，经常变动的信息

#### Zset

有序集合，在set集合基础上加了排序

```shell
127.0.0.1:6379> ZADD myzset 1 one  // 添加一个值
(integer) 1
127.0.0.1:6379> ZADD myzset 2 two 3 three   // 添加多个值
(integer) 2
127.0.0.1:6379> ZRANGE myzset 0 -1
1) "one"
2) "two"
3) "three"

127.0.0.1:6379> zadd salary 2500 xiaohong
(integer) 1
127.0.0.1:6379> zadd salary 5000 zhangsan
(integer) 1
127.0.0.1:6379> zadd salary 500 kxj
(integer) 1
127.0.0.1:6379> ZRANGEBYSCORE salary
(error) ERR wrong number of arguments for 'zrangebyscore' command
127.0.0.1:6379> ZRANGEBYSCORE salary -inf +inf
1) "kxj"
2) "xiaohong"
3) "zhangsan"
127.0.0.1:6379> ZRANGE myzset 0 -1 # 默认从小到大排序
1) "xiaoming"
2) "zhangsan"
3) "lisi"
127.0.0.1:6379> ZREVRANGE salary 0 -1 #从大到小排序
1) "zhangsan"
2) "kxj"
127.0.0.1:6379> ZRANGEBYSCORE salary -inf +inf withscores
1) "kxj"
2) "500"
3) "xiaohong"
4) "2500"
5) "zhangsan"
6) "5000"


127.0.0.1:6379> ZREM salary xiaohong  # 删除
(integer) 1
127.0.0.1:6379> zrange salary 0 -1
1) "kxj"
2) "zhangsan"
127.0.0.1:6379> zcard salary  # 获取个数
(integer) 2

```

### 三种特殊数据类型

#### geospatial 地理位置

命令

```shell
GEOADD # 添加
GEODIST # 查询两个元素的直线距离
GEOHASH
GEOPOS # 查询元素的经纬度
GEORADIUS # 以给定的经纬度为中心， 返回键包含的位置元素当中， 与中心的距离不超过给定最大距离的所有位置元素
GEORADIUSBYMEMBER # 找出位于指定范围内的元素， 但是 GEORADIUSBYMEMBER 的中心点是由给定的位置元素决定的， 而不是像 GEORADIUS 那样， 使用输入的经度和纬度来决定中心点
```

#### hyperloglog

基数统计的算法

优点：占用的内存是固定的

#### Bitmaps

位存储

适用于两个状态，例如，考勤，未考勤

### 事务

Redis单条命令是保证原子性，但是事务不保证原子性

Redis事务没有隔离级别的概念

所有命令在事务中，并没有直接被执行，只有发起执行命令的时候才会被执行

Redis事务本质：一组命令的集合。一个事务中的所有命令都会被序列化，在事务执行的过程中，会按照顺序执行。

特性：

一次性，顺序性，排他性

redis的事务

- 开启事务（multi）

- 命令入队

- 执行事务（exec)

  ```shell
  127.0.0.1:6379> MULTI
  OK
  127.0.0.1:6379> set k1 v1 
  QUEUED
  127.0.0.1:6379> set k2 v2
  QUEUED
  127.0.0.1:6379> get v2
  QUEUED
  127.0.0.1:6379> set k3 v3
  QUEUED
  127.0.0.1:6379> EXEC
  1) OK
  2) OK
  3 (nil)
  4) OK
  ```

放弃事务

```shell
discard
```



异常

- 编译时异常

  代码又问题，命令有错。事务中的所有命令都不会被执行

  ```shell
  127.0.0.1:6379> MULTI
  OK
  127.0.0.1:6379> set k1 v1
  QUEUED
  127.0.0.1:6379> set k2 v2
  QUEUED
  127.0.0.1:6379> getset k2  # 错误的命令
  (error) ERR wrong number of arguments for 'getset' command
  127.0.0.1:6379> EXEC # 执行事务报错
  (error) EXECABORT Transaction discarded because of previous errors.
  127.0.0.1:6379> get k1 # 所有的命令都不会执行
  (nil)
  
  ```

  

- 运行时异常

  如果事务中存在语法性，那么执行命令的时候，其他命令是可以正常执行的，错误命令抛出异常

  ```shell
  127.0.0.1:6379> MULTI
  OK
  127.0.0.1:6379> set k1 v1
  QUEUED
  127.0.0.1:6379> set k2 v2
  QUEUED
  127.0.0.1:6379> INCR k2
  QUEUED
  127.0.0.1:6379> set k3 v3
  QUEUED
  127.0.0.1:6379> EXEC
  1) OK
  2) OK
  3) (error) ERR value is not an integer or out of range
  4) OK
  127.0.0.1:6379> get k1
  "v1"
  127.0.0.1:6379> get k3
  "v3"
  ```


### 悲观锁和乐观锁

#### 悲观锁

认为什么时候都会出现问题，无论做什么都会加锁

#### 乐观锁

认为什么时候都不会出现问题，所以不会上锁，更新数据的时候去判断一下。在此期间是否有人修改过这个数据，mysql通过version来进行判断

redis 乐观锁使用watch(监控)

```shell
127.0.0.1:6379>watch money  # 监控
OK 
127.0.0.1:6379>multi
OK 
127.0.0.1:6379>decrby money 10
OK 
127.0.0.1:6379>incrby out 10
OK 
127.0.0.1:6379>exec	# 修改失败，执行之前另外另一个线程修改了我们的值
nil # 修改失败，执行之前另外另一个线程修改了我们的值

127.0.0.1:6379> unwatch # 如果事务执行失败，就先解锁
OK
127.0.0.1:6379> watch money # 获取最新的值，再次监视，select version
127.0.0.1:6379> multi
OK
127.0.0.1:6379>decrby money 10
OK 
127.0.0.1:6379>incrby out 10
OK 

```



### Jedis

Jedis是Redis官方推荐的Java连接工具，使用 java 操作Redis中间件。

1. 导入依赖

   ```shell
   
   ```

2. 编码测试

   1、连接数据库

   2、操作命令

   3、断开命令

### Spring Boot 集成 Redis 

Spring Boot 2.x之后，原来使用的jedis被替换成了lettuce

jedis： Jedis是直接连接Redis，非线程安全，在性能上，每个线程都去拿自己的 Jedis 实例，当连接数量增多时，资源消耗阶梯式增大，连接成本就较高了 。BIO

luttuce：采用netty，实例可以在多个线程中进行共享，不存在线程不安全的情况，可以减少线程数据，NIO



### Redis.conf







