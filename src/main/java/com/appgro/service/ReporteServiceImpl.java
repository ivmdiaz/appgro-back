package com.appgro.service;

import static com.appgro.util.AppgroUtil.getNombreUsuarioLogueado;
import static com.appgro.util.ExceptionUtil.exceptiontoEstadoResponse;
import static com.appgro.util.ExceptionUtil.negocioExceptiontoEstadoResponse;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.appgro.exception.NegocioException;
import com.appgro.model.entity.ViewVentasTodas;
import com.appgro.model.request.ReporteCampoRequest;
import com.appgro.model.request.ReporteFiltroRequest;
import com.appgro.model.request.ReporteRequest;
import com.appgro.model.response.EntidadResponse;
import com.appgro.model.response.ReporteProductoResponse;
import com.appgro.model.response.ReporteTiendaResponse;
import com.appgro.repository.GenUsuarioRepository;
import com.appgro.repository.ReportesRepository;
import com.appgro.util.AppgroUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ReporteServiceImpl implements ReporteService {

	static final Logger log = Logger.getLogger(ReporteServiceImpl.class.getName());

	private static final String VENTAS_TODAS = "ventas_todas";
	private static final String VENTAS_TIENDA = "ventas_tiendas";
	private static final String VENTAS_PRODUCTO = "ventas_productos";
	
	private final ReportesRepository mReportesRepository;
	private final GenUsuarioRepository mUsuarioRepository;
	
	public ReporteServiceImpl(ReportesRepository mReportesRepository, 
			final GenUsuarioRepository mUsuarioRepository) {
		this.mReportesRepository = mReportesRepository;
		this.mUsuarioRepository = mUsuarioRepository;
	}

	@Override
	public EntidadResponse<String> obtenerReporte(final ReporteRequest reporte) {
		EntidadResponse<String> response = new EntidadResponse<>();
		try {

			// Obtenemos el id usuario del JWT @NotNull
			Long idUsuario = mUsuarioRepository.findIdUsuarioByCorreo(getNombreUsuarioLogueado());
						
			String entidad = null;

			if (VENTAS_TODAS.equals(reporte.getReporte())) {
				entidad = this.obtenerReporteVentasTodas(reporte, idUsuario);
			} else if (VENTAS_PRODUCTO.equals(reporte.getReporte())) {
				entidad = this.obtenerReporteProductos(reporte, idUsuario);
			} else if (VENTAS_TIENDA.equals(reporte.getReporte())) {
				entidad = this.obtenerReporteTiendas(reporte, idUsuario);
			}

			response.setEntidad(entidad);
			response.setEstado(AppgroUtil.R0067);

		} catch (NegocioException ex) {
			response.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			response.setEstado(exceptiontoEstadoResponse(ex));
		}

		return response;
	}
	
	@Override
	public EntidadResponse<String> obtenerReporteAdministrador(ReporteRequest reporte) {
		EntidadResponse<String> response = new EntidadResponse<>();
		try {
			response.setEntidad(this.obtenerReporteAdmTiendas(reporte));
			response.setEstado(AppgroUtil.R0067);
		} catch (NegocioException ex) {
			response.setEstado(negocioExceptiontoEstadoResponse(ex));
		} catch (Exception ex) {
			response.setEstado(exceptiontoEstadoResponse(ex));
		}

		return response;
	}
	
	/* FUNCIONES PARA OBTENER REPORTES SEGUN PARÁMETROS */
	
	private String obtenerReporteVentasTodas(final ReporteRequest reporte, final Long idVendedor) throws Exception {
		String title = "Reporte de ventas por tienda y producto";
		
		final List<ReporteFiltroRequest> filter = reporte.getFiltros();
		final List<Integer> idsTienda = getFilterIn(filter, "idTienda");
		final List<Integer> idsProducto = getFilterIn(filter, "idProducto");		
		final List<Integer> idsCategoriaProducto = getFilterIn(filter, "idCategoriaProducto");
		final Double totalVentaMayor = getFilterNumGreaterThan(filter, "totalVenta");
		final Double totalVentaMenor = getFilterNumLessThan(filter, "totalVenta");
		final List<Integer> idDepartamentoVenta = getFilterIn(filter, "idDepartamentoVenta");
		final List<Integer> idCiudadVenta = getFilterIn(filter, "idCiudadVenta");
		final Date fechaVentaMayor = getFilterDateGreaterThan(filter, "fechaVenta");
		final String estado = getFilterEquals(filter, "estado");


		//Obtener datos:
		List<ViewVentasTodas> datasource = mReportesRepository.getReporteTodasVentas(
				idVendedor, 
				idsTienda, 
				idsProducto, 
				idsCategoriaProducto, 
				totalVentaMayor, 
				totalVentaMenor, 
				idDepartamentoVenta, 
				idCiudadVenta, 
				fechaVentaMayor,
				estado
				);
		
		if(CollectionUtils.isEmpty(datasource)) {
			throw new NegocioException(AppgroUtil.R0068.getMensaje());
		}
		
		//Obtener reporte:
		return obtenerReporte(title, reporte, datasource);
	}
	
	private String obtenerReporteTiendas(final ReporteRequest reporte, final Long idVendedor) throws Exception {
		String title = "Reporte de ventas por tienda";
		
		final List<ReporteFiltroRequest> filter = reporte.getFiltros();
		final List<Integer> idsTienda = getFilterIn(filter, "idTienda");
		final List<Integer> idDepartamentoVenta = getFilterIn(filter, "idDepartamentoVenta");
		final List<Integer> idCiudadVenta = getFilterIn(filter, "idCiudadVenta");
		final Date fechaVentaMayor = getFilterDateGreaterThan(filter, "fechaVenta");
		final String estado = getFilterEquals(filter, "estado");


		//Obtener datos:
		List<ReporteTiendaResponse> datasource = mReportesRepository.getReporteTiendas(
				idVendedor, 
				idsTienda, 
				idDepartamentoVenta, 
				idCiudadVenta, 
				fechaVentaMayor,
				estado
				);
		
		if(CollectionUtils.isEmpty(datasource)) {
			throw new NegocioException(AppgroUtil.R0068.getMensaje());
		}
		
		//Obtener reporte:
		return obtenerReporte(title, reporte, datasource);
	}
	
	private String obtenerReporteProductos(final ReporteRequest reporte, final Long idVendedor) throws Exception {
		String title = "Reporte de ventas por producto";
		
		final List<ReporteFiltroRequest> filter = reporte.getFiltros();
		final List<Integer> idsTienda = getFilterIn(filter, "idTienda");
		final List<Integer> idsProducto = getFilterIn(filter, "idProducto");		
		final List<Integer> idsCategoriaProducto = getFilterIn(filter, "idCategoriaProducto");
		final List<Integer> idDepartamentoVenta = getFilterIn(filter, "idDepartamentoVenta");
		final List<Integer> idCiudadVenta = getFilterIn(filter, "idCiudadVenta");
		final Date fechaVentaMayor = getFilterDateGreaterThan(filter, "fechaVenta");
		final String estado = getFilterEquals(filter, "estado");


		//Obtener datos:
		List<ReporteProductoResponse> datasource = mReportesRepository.getReporteProductos(
				idVendedor, 
				idsTienda, 
				idsProducto, 
				idsCategoriaProducto, 
				idDepartamentoVenta, 
				idCiudadVenta, 
				fechaVentaMayor,
				estado
				);
		
		if(CollectionUtils.isEmpty(datasource)) {
			throw new NegocioException(AppgroUtil.R0068.getMensaje());
		}
		
		//Obtener reporte:
		return obtenerReporte(title, reporte, datasource);
	}
	
	private String obtenerReporteAdmTiendas(final ReporteRequest reporte) throws Exception {
		String title = "Reporte de administración por tiendas";
		
		final List<ReporteFiltroRequest> filter = reporte.getFiltros();
		final List<Integer> idsTienda = getFilterIn(filter, "idTienda");
		final List<Integer> idDepartamentoVenta = getFilterIn(filter, "idDepartamentoVenta");
		final List<Integer> idCiudadVenta = getFilterIn(filter, "idCiudadVenta");
		final Date fechaVentaMayor = getFilterDateGreaterThan(filter, "fechaVenta");
		final Date fechaVentaMenor = getFilterDateLessThan(filter, "fechaVenta");
		final String estado = getFilterEquals(filter, "estado");


		//Obtener datos:
		List<ReporteTiendaResponse> datasource = mReportesRepository.getReporteAdmTiendas(
				idsTienda, 
				idDepartamentoVenta, 
				idCiudadVenta, 
				fechaVentaMayor,
				fechaVentaMenor,
				estado
				);
		
		if(CollectionUtils.isEmpty(datasource)) {
			throw new NegocioException(AppgroUtil.R0068.getMensaje());
		}
		
		//Obtener reporte:
		return obtenerReporte(title, reporte, datasource);
	}
	
	/* FUNCIONES PARA REALIZAR FILTROS */
	
	private List<Integer> getFilterIn(List<ReporteFiltroRequest> filters, String field) {
		ReporteFiltroRequest filter = filters.stream()
				.filter(f -> (f != null && Objects.equals(f.getCampo(), field)))
				.findFirst().orElse(null);
		if (Objects.nonNull(filter) && !CollectionUtils.isEmpty(filter.getValorEntreIds())) {
			return filter.getValorEntreIds();
		}
		return null;
	}
	
	private Double getFilterNumGreaterThan(List<ReporteFiltroRequest> filters, String field) {
		ReporteFiltroRequest filter = filters.stream()
				.filter(f -> (f != null && Objects.equals(f.getCampo(), field)))
				.findFirst().orElse(null);
		if (Objects.nonNull(filter) && Objects.nonNull(filter.getNumeroMayorA())) {
			return filter.getNumeroMayorA();
		}
		return null;
	}
	
	private Double getFilterNumLessThan(List<ReporteFiltroRequest> filters, String field) {
		ReporteFiltroRequest filter = filters.stream()
				.filter(f -> (f != null && Objects.equals(f.getCampo(), field)))
				.findFirst().orElse(null);
		if (Objects.nonNull(filter) && Objects.nonNull(filter.getNumeroMenorA())) {
			return filter.getNumeroMenorA();
		}
		return null;
	}
	
	private Date getFilterDateGreaterThan(List<ReporteFiltroRequest> filters, String field) {
		ReporteFiltroRequest filter = filters.stream()
				.filter(f -> (f != null && Objects.equals(f.getCampo(), field)))
				.findFirst().orElse(null);
		if (Objects.nonNull(filter) && Objects.nonNull(filter.getFechaMayorA())) {
			return filter.getFechaMayorA();
		}
		return null;
	}
	
	private Date getFilterDateLessThan(List<ReporteFiltroRequest> filters, String field) {
		ReporteFiltroRequest filter = filters.stream()
				.filter(f -> (f != null && Objects.equals(f.getCampo(), field)))
				.findFirst().orElse(null);
		if (Objects.nonNull(filter) && Objects.nonNull(filter.getFechaMenorA())) {
			return filter.getFechaMenorA();
		}
		return null;
	}
	
	private String getFilterEquals(List<ReporteFiltroRequest> filters, String field) {
		ReporteFiltroRequest filter = filters.stream()
				.filter(f -> (f != null && Objects.equals(f.getCampo(), field)))
				.findFirst().orElse(null);
		if (Objects.nonNull(filter) && Objects.nonNull(filter.getValorIgualA())) {
			return filter.getValorIgualA();
		}
		return null;
	}
	
	/* FUNCIONES PARA REALIZAR REPORTE */
	
	private <T> String obtenerReporte(String title, ReporteRequest reporte, List<T> datasource) throws Exception {
		Document document = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

			document = new Document();
			
			// Orientación:
			if (reporte.getCampos().size() > 5) {
				document.setPageSize(PageSize.A4.rotate());
			}
			
			// Margenes:
			document.setMargins(50f, 50f, 50f, 50f);
			
			PdfWriter.getInstance(document, baos);
			document.open();
			
			

			// Sección de titulo:
			addTitleToDocument(title, document);

			// Sección de tabla:
			addTableToDocument(reporte, datasource, document);

			document.close();

			byte[] bytes = baos.toByteArray();
			String base64 = Base64.getEncoder().encodeToString(bytes);

			return base64;

		} finally {
			if (document != null && document.isOpen()) {
				document.close();
			}
		}
	}

	private void addTitleToDocument(String title, Document document) throws Exception {

		final Font font = new Font(FontFamily.HELVETICA);
		font.setColor(BaseColor.BLACK);
		font.setSize(14F);

		final Paragraph titleElement = new Paragraph(title, font);
		titleElement.setAlignment(Element.ALIGN_CENTER);

		document.add(titleElement);
		document.add(new Paragraph("\n"));
	}

	private <T> void addTableToDocument(ReporteRequest reporte, List<T> datasource, Document document)
			throws Exception {
		PdfPTable table = new PdfPTable(reporte.getCampos().size());
		table.setWidthPercentage(100f);
		addHeaderToTable(reporte, table);
		addDataToTable(reporte, datasource, table);
		document.add(table);
	}

	private void addHeaderToTable(ReporteRequest reporte, PdfPTable table) {
		for (ReporteCampoRequest campo : reporte.getCampos()) {

			final Font font = new Font(FontFamily.HELVETICA);
			font.setColor(BaseColor.WHITE);
			font.setSize(10F);

			final PdfPCell cell = new PdfPCell(new Phrase(campo.getCabecera(), font));
			cell.setFixedHeight(20F);
			cell.setBackgroundColor(new BaseColor(83, 109, 254));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);

			table.addCell(cell);
		}
	}

	private <T> void addDataToTable(ReporteRequest reporte, List<T> datasource, PdfPTable table) throws Exception {
		final Font font = new Font(FontFamily.HELVETICA);
		font.setSize(10F);

		for (T entity : datasource) {
			for (ReporteCampoRequest campo : reporte.getCampos()) {
				String value = getValueFromDS(entity, campo.getCampo());
				final PdfPCell cell = new PdfPCell(new Phrase(value, font));
				cell.setFixedHeight(18F);
				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
				table.addCell(new Phrase(value, font));
			}
		}
	}

	public <T> String getValueFromDS(T obj, String campo) throws Exception {
		String nameGetMethod = String.format("get%s", campo.substring(0, 1).toUpperCase() + campo.substring(1));
		Method getMethod = obj.getClass().getMethod(nameGetMethod);
		Object objValue = getMethod.invoke(obj);

		String value = "";
		if (objValue != null) {
			value = String.valueOf(objValue);
			if (objValue instanceof Date) {
				Format formatter = new SimpleDateFormat("yyyy-MM-dd");
				value = formatter.format(objValue);
			}
		}

		return value;
	}

}
