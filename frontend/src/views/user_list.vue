<template>
  <div class="mt-6">
    <v-row>
      <v-col cols="2">
        <admin_sidebar></admin_sidebar>
      </v-col>
      <v-col cols="10">
        <v-data-table
          :headers="headers"
          :items="userList"
          :items-per-page="5"
          class="elevation-1"
        >
          <template v-slot:item.action="{ item }">
            <v-btn
              class="mr-2"
              color="primary"
              fab
              x-small
              elevation="2"
              @click="
                updateDialog = true;
                toUpdateStatus = Object.assign({}, item);
              "
            >
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
          </template>
        </v-data-table>

        <v-dialog v-model="updateDialog" width="400">
          <v-card>
            <v-toolbar color="primary" dark>
              <div>Update Status</div>
              <v-spacer></v-spacer>
              <v-btn icon @click="updateDialog = false">
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar>
            <v-card-text class="pa-4">
              <v-row dense>
                <v-col cols="3" class="font-weight-bold">ID</v-col>
                <v-col cols="7">{{ toUpdateStatus.id }}</v-col>
                <v-col cols="3" class="font-weight-bold">Email</v-col>
                <v-col cols="7">{{ toUpdateStatus.email }}</v-col>
                <v-col cols="3"></v-col>
                <v-col cols="7">
                  <v-select
                    label="Status"
                    :items="userStatus"
                    v-model="toUpdateStatus.userStatus"
                  >
                  </v-select>
                </v-col>
              </v-row>
            </v-card-text>
            <v-card-actions class="justify-end">
              <v-btn color="info" dark @click="updateStatus()">Update</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import admin_sidebar from "@/components/admin_sidebar.vue";
import utils from "@/utils/utils";

export default {
  name: "user_list",
  components: {
    admin_sidebar,
  },
  data() {
    return {
      headers: [
        { text: "Unique Id", value: "id", sortable: true },
        { text: "Username", value: "username", sortable: true },
        { text: "Email", value: "email", sortable: true },
        { text: "Created At", value: "createdAt", sortable: true },
        { text: "Updated At", value: "updatedAt", sortable: true },
        { text: "User Status", value: "userStatus", sortable: true },
        { text: "Action", value: "action", sortable: false },
      ],
      userList: [],
      userStatus: [],
      updateDialog: false,
      toUpdateStatus: {
        id: null,
      },
    };
  },
  async created() {
    await this.getUserList();
    await this.getUserStatus();
  },
  methods: {
    async getUserList() {
      const resp = await utils.http.get("/api/admin/user/list");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          // console.log(data);
          this.userList = data;
        }
      }
    },
    async getUserStatus() {
      const resp = await utils.http.get("/api/admin/user/status");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          // console.log(data);
          this.userStatus = data;
        }
      }
    },
    async updateStatus() {
      const resp = await utils.http.put(
        "/api/admin/user/status/update?userId=" +
          this.toUpdateStatus.id +
          "&status=" +
          this.toUpdateStatus.userStatus
      );
      if (resp && resp.status === 200) {
        await this.getUserList();
        this.updateDialog = false;
      }
    },
  },
};
</script>

<style></style>
