package com.fieldops.ui;

import com.fieldops.application.FieldOpsData;
import com.fieldops.model.Employee;
import com.fieldops.model.Team;
import com.fieldops.model.Vehicle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

// Janela principal do sistema FieldOps.
public class FieldOpsFrame extends JFrame {
    private static final Color BACKGROUND = new Color(245, 247, 250);
    private static final Color SIDEBAR = new Color(20, 33, 61);
    private static final Color SIDEBAR_ACTIVE = new Color(42, 64, 115);
    private static final Color TEXT_DARK = new Color(30, 41, 59);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color CARD_BORDER = new Color(226, 232, 240);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font SECTION_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private final FieldOpsData data;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    public FieldOpsFrame(FieldOpsData data) {
        this.data = data;

        configureWindow();
        configureLookAndFeel();
        buildLayout();
    }

    // Configura tamanho, titulo e comportamento da janela.
    private void configureWindow() {
        setTitle("FieldOps - Gestao de Operacoes");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 680));
        setLocationRelativeTo(null);
    }

    // Ajusta detalhes visuais padrao do Swing.
    private void configureLookAndFeel() {
        UIManager.put("Table.font", BODY_FONT);
        UIManager.put("Table.rowHeight", 38);
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 13));
    }

    // Monta a estrutura principal: menu lateral e area de conteudo.
    private void buildLayout() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);
        root.add(createSidebar(), BorderLayout.WEST);

        contentPanel.setBackground(BACKGROUND);
        contentPanel.add(createDashboardPanel(), "dashboard");
        contentPanel.add(createEmployeesPanel(), "employees");
        contentPanel.add(createVehiclesPanel(), "vehicles");
        contentPanel.add(createTeamsPanel(), "teams");
        root.add(contentPanel, BorderLayout.CENTER);

        setContentPane(root);
        cardLayout.show(contentPanel, "dashboard");
    }

    // Cria o menu lateral de navegacao.
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(28, 18, 18, 18));
        sidebar.setLayout(new GridLayout(8, 1, 0, 10));

        JLabel brand = new JLabel("FieldOps");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Segoe UI", Font.BOLD, 28));
        sidebar.add(brand);

        sidebar.add(createNavigationButton("Dashboard", "dashboard"));
        sidebar.add(createNavigationButton("Colaboradores", "employees"));
        sidebar.add(createNavigationButton("Veiculos", "vehicles"));
        sidebar.add(createNavigationButton("Equipes", "teams"));
        sidebar.add(Box.createVerticalStrut(1));
        sidebar.add(createSidebarInfo("Etapa 4"));
        sidebar.add(createSidebarInfo("Interface desktop"));

        return sidebar;
    }

    // Cria um botao do menu lateral.
    private JButton createNavigationButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBackground(SIDEBAR_ACTIVE);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(10, 14, 10, 14));
        button.addActionListener(event -> cardLayout.show(contentPanel, cardName));
        return button;
    }

    // Cria textos pequenos no rodape do menu lateral.
    private JLabel createSidebarInfo(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(203, 213, 225));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return label;
    }

    // Cria o painel inicial com indicadores resumidos.
    private JPanel createDashboardPanel() {
        JPanel page = createPage("Dashboard operacional", "Visao geral dos recursos cadastrados em memoria.");

        JPanel cards = new JPanel(new GridLayout(1, 3, 18, 18));
        cards.setOpaque(false);
        cards.add(createMetricCard("Colaboradores", data.employeeService().listAll().size(), "Pessoas cadastradas"));
        cards.add(createMetricCard("Veiculos", data.vehicleService().listAll().size(), "Frota operacional"));
        cards.add(createMetricCard("Equipes", data.teamService().listAll().size(), "Times montados"));
        page.add(cards);

        JPanel summary = createCardPanel();
        summary.setLayout(new GridLayout(3, 1, 0, 8));
        summary.add(createSummaryLine("Equipe ativa", data.teamService().listAll().getFirst().getName()));
        summary.add(createSummaryLine("Lider", data.teamService().listAll().getFirst().getLeader().getName()));
        summary.add(createSummaryLine("Veiculo principal", data.teamService().listAll().getFirst().getVehicle().getLicensePlate()));
        page.add(summary);

        return wrapPage(page);
    }

    // Cria a pagina com tabela de colaboradores.
    private JPanel createEmployeesPanel() {
        JPanel page = createPage("Colaboradores", "Cadastro atual de profissionais da operacao.");
        String[] columns = {"ID", "Nome", "Matricula", "Cargo", "Turno", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Employee employee : data.employeeService().listAll()) {
            model.addRow(new Object[]{
                    employee.getId(),
                    employee.getName(),
                    employee.getRegistration(),
                    employee.getRole(),
                    employee.getWorkShift(),
                    employee.getStatus()
            });
        }

        page.add(createTableCard(model));
        return wrapPage(page);
    }

    // Cria a pagina com tabela de veiculos.
    private JPanel createVehiclesPanel() {
        JPanel page = createPage("Veiculos", "Frota disponivel para atendimento de campo.");
        String[] columns = {"ID", "Placa", "Modelo", "Tipo", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Vehicle vehicle : data.vehicleService().listAll()) {
            model.addRow(new Object[]{
                    vehicle.getId(),
                    vehicle.getLicensePlate(),
                    vehicle.getModel(),
                    vehicle.getType(),
                    vehicle.getStatus()
            });
        }

        page.add(createTableCard(model));
        return wrapPage(page);
    }

    // Cria a pagina com tabela de equipes.
    private JPanel createTeamsPanel() {
        JPanel page = createPage("Equipes", "Composicao das equipes de operacao.");
        String[] columns = {"ID", "Nome", "Lider", "Membros", "Veiculo"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Team team : data.teamService().listAll()) {
            model.addRow(new Object[]{
                    team.getId(),
                    team.getName(),
                    team.getLeader().getName(),
                    formatMembers(team.getMembers()),
                    team.getVehicle().getLicensePlate()
            });
        }

        page.add(createTableCard(model));
        return wrapPage(page);
    }

    // Cria a base visual de cada pagina.
    private JPanel createPage(String title, String subtitle) {
        JPanel page = new JPanel();
        page.setOpaque(false);
        page.setLayout(new GridLayout(0, 1, 0, 18));

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_DARK);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(BODY_FONT);
        subtitleLabel.setForeground(TEXT_MUTED);

        header.add(titleLabel);
        header.add(subtitleLabel);
        page.add(header);

        return page;
    }

    // Aplica espacamento externo na pagina.
    private JPanel wrapPage(JPanel page) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BACKGROUND);
        wrapper.setBorder(new EmptyBorder(30, 34, 30, 34));
        wrapper.add(page, BorderLayout.NORTH);
        return wrapper;
    }

    // Cria um cartao de indicador numerico.
    private JPanel createMetricCard(String title, int value, String subtitle) {
        JPanel card = createCardPanel();
        card.setLayout(new GridLayout(3, 1, 0, 4));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(BODY_FONT);
        titleLabel.setForeground(TEXT_MUTED);

        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
        valueLabel.setForeground(TEXT_DARK);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(TEXT_MUTED);

        card.add(titleLabel);
        card.add(valueLabel);
        card.add(subtitleLabel);
        return card;
    }

    // Cria uma linha de resumo no dashboard.
    private JLabel createSummaryLine(String label, String value) {
        JLabel summary = new JLabel(label + ": " + value);
        summary.setFont(SECTION_FONT);
        summary.setForeground(TEXT_DARK);
        return summary;
    }

    // Cria um card que contem uma tabela.
    private JPanel createTableCard(DefaultTableModel model) {
        JPanel card = createCardPanel();
        card.setLayout(new BorderLayout());

        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    // Define cores e fontes das tabelas.
    private void styleTable(JTable table) {
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(TEXT_DARK);
        table.setForeground(TEXT_DARK);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(241, 245, 249));
        header.setForeground(TEXT_DARK);
        header.setPreferredSize(new Dimension(0, 42));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                );
                component.setFont(BODY_FONT);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                if (!isSelected) {
                    component.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                }
                return component;
            }
        };

        for (int column = 0; column < table.getColumnCount(); column++) {
            table.getColumnModel().getColumn(column).setCellRenderer(renderer);
        }
    }

    // Cria um painel branco com borda leve, usado como card.
    private JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER),
                new EmptyBorder(18, 18, 18, 18)
        ));
        return card;
    }

    // Junta os nomes dos membros em um texto unico para aparecer na tabela.
    private String formatMembers(List<Employee> members) {
        return members.stream()
                .map(Employee::getName)
                .reduce((first, second) -> first + ", " + second)
                .orElse("-");
    }
}
