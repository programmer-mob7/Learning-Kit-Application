package com.example.learningkitapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SimCardDownload
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learningkitapplication.ui.theme.LearningKitApplicationTheme

data class Supplier(
    val name: String,
    val location: String,
    val orderNo: String,
    val date: String,
    val status: SupplierStatus,
    val contactPerson: String
)

enum class SupplierStatus {
    ACTIVE, INACTIVE
}

enum class SupplierAction {
    ACTIVATE, INACTIVATE, DELETE
}

val suppliers = listOf(
    Supplier("PT. ABC Indonesia", "Jakarta Utara, Indonesia", "473112312132123151651615611515121354412", "Fri 29 Sept 2023 13:00:01", SupplierStatus.ACTIVE, "Nakamoto Y"),
    Supplier("PT. Sinar Mas Dunia", "Jakarta Utara, Indonesia", "47324731123121321231516516156115151213", "Fri 29 Sept 2023 13:00:01", SupplierStatus.INACTIVE, "Mark L"),
    Supplier("PT. GHI Indonesia", "Jakarta Utara, Indonesia", "47334731123121321231516516156115151213", "Fri 29 Sept 2023 13:00:01", SupplierStatus.ACTIVE, "Karina Y"),
    Supplier("PT. Ichitan Indonesia", "Jakarta Utara, Indonesia", "47344731123121321231516516156115151213", "Fri 29 Sept 2023 13:00:01", SupplierStatus.INACTIVE, "Hong E")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearningKitApplicationTheme {
                SupplierScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplierScreen() {
    var selectedTab by remember { mutableStateOf("list") }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            Column(
                modifier = Modifier
                    .background(Color(0xFF00A455))
            ) {
                LargeTopAppBar(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
                    title = { Text("Supplier") },
                    navigationIcon = {
                        IconButton(onClick = { /* Back */ }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { }) { Icon(Icons.Default.Search, contentDescription = "Cari") }
                        IconButton(onClick = { }) { Icon(Icons.Default.FilterAlt, contentDescription = "Filter") }
                        IconButton(onClick = { }) { Icon(Icons.Default.SimCardDownload, contentDescription = "Unduh") }
                        IconButton(onClick = { }) { Icon(Icons.Default.ListAlt, contentDescription = "List") }
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color(0xFF00A455),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SupplierToggleBar(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it },
                        modifier = Modifier.width(350.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            when (selectedTab) {
                "list" -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(suppliers) { supplier ->
                            SupplierItem(supplier)
                        }
                    }
                }
                "activities" -> {
                    Text(
                        "Aktivitas Supplier",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SupplierToggleBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf("list", "activities")
    val titles = listOf("List", "Supplier Activities")

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        tabs.forEachIndexed { index, tab ->
            val isSelected = selectedTab == tab

            Box(
                modifier = Modifier
                    .weight(1f) // biar rata
                    .clip(RoundedCornerShape(16))
                    .background(if (isSelected) Color(0xFF00A455) else Color.White)
                    .clickable { onTabSelected(tab) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = titles[index],
                    color = if (isSelected) Color.White else Color(0xFF00A455),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplierItem(supplier: Supplier) {
    var showSheet by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedAction by remember { mutableStateOf<SupplierAction?>(null) }
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFDAD9E3))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatusBadge(supplier.status)
                IconButton(onClick = { showSheet = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                }
            }

            Text(
                text = supplier.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(text = supplier.location, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFE0E0E0))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Order: ${supplier.orderNo}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                    modifier = Modifier.widthIn(max = 82.dp),
                    overflow = TextOverflow.MiddleEllipsis,
                    softWrap = false,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = supplier.date, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Start)
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.Person, contentDescription = "Contact", tint = Color(0xFF00A455))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = supplier.contactPerson,
                    color = Color(0xFF00A455),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            containerColor = Color.White,
            dragHandle = { }
        ) {

            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = {
                    selectedAction = SupplierAction.ACTIVATE
                    showDialog = true
                    showSheet = false
                }) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF00A455))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Activate", color = Color.Black)
                }

                Divider(thickness = 1.dp, color = Color(0xFFE0E0E0))

                TextButton(onClick = {
                    selectedAction = SupplierAction.INACTIVATE
                    showDialog = true
                    showSheet = false
                }) {
                    Icon(Icons.Default.Clear, contentDescription = null, tint = Color(0xFFFFA500))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Inactivate", color = Color.Black)
                }

                Divider(thickness = 1.dp, color = Color(0xFFE0E0E0))

                TextButton(onClick = {
                    selectedAction = SupplierAction.DELETE
                    showDialog = true
                    showSheet = false
                }) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete", color = Color.Red)
                }
            }
        }
    }

    if (showDialog && selectedAction != null) {
        var title = ""
        var message = ""
        var icon = Icons.Default.Info
        var iconTint = Color.Black
        val onDismiss: () -> Unit = {
            showDialog = false
            selectedAction = null
        }

        when (selectedAction) {
            SupplierAction.ACTIVATE -> {
                title = "Activate Supplier"
                message = "${supplier.name} will be activated. Are you sure?"
                icon = Icons.Default.CheckCircleOutline
                iconTint = Color(0xFF00A455)
            }
            SupplierAction.INACTIVATE -> {
                title = "Inactivate Supplier"
                message = "${supplier.name} will be inactivated. Are you sure?"
                icon = Icons.Default.WarningAmber
                iconTint = Color.Red
            }
            SupplierAction.DELETE -> {
                title = "Delete Supplier"
                message = "${supplier.name} will be deleted. Are you sure?"
                icon = Icons.Default.Delete
                iconTint = Color.Red
            }
            null -> {}
        }

        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon, contentDescription = null, tint = iconTint)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(title)
                }
            },
            text = { Text(message) },
            confirmButton = {

                TextButton(onClick = {
                    onDismiss()
                    when (selectedAction) {
                        SupplierAction.ACTIVATE -> Toast.makeText(context, "Supplier activated", Toast.LENGTH_SHORT).show()
                        SupplierAction.INACTIVATE -> Toast.makeText(context, "Supplier inactivated", Toast.LENGTH_SHORT).show()
                        SupplierAction.DELETE -> Toast.makeText(context, "Supplier deleted", Toast.LENGTH_SHORT).show()
                        null -> {}
                    }
                }) {
                    Text(selectedAction!!.name, color = iconTint, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        )
    }
}


@Composable
fun StatusBadge(status: SupplierStatus) {
    val (text, bgColor, textColor) = when (status) {
        SupplierStatus.ACTIVE -> Triple("Active", Color(0xFFDFF5E1), Color(0xFF00A455))
        SupplierStatus.INACTIVE -> Triple("Inactive", Color(0xFFFFE0E0), Color(0xFFD32F2F))
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(0.dp,8.dp,8.dp,0.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, color = textColor, style = MaterialTheme.typography.bodySmall)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSupplierScreen() {
    LearningKitApplicationTheme {
        SupplierScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSupplierItem() {
    LearningKitApplicationTheme {
        SupplierItem(suppliers[0])
    }
}
